package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.model.Petugas;
import com.example.demo.repo.AdminRepository;
import com.example.demo.repo.PetugasRepository;
import com.example.demo.repo.WargaRepository;

/**
 * Membaca file Excel (sheet pertama, baris pertama = nama kolom) lalu menyimpan
 * data Petugas atau Admin. Baris yang salah dilewati dan dilaporkan.
 */
@Service
public class ImportDataService {

    public record Hasil(int berhasil, List<String> gagal) {
    }

    private final AdminRepository adminRepo;
    private final PetugasRepository petugasRepo;
    private final WargaRepository wargaRepo;

    public ImportDataService(AdminRepository adminRepo, PetugasRepository petugasRepo,
                             WargaRepository wargaRepo) {
        this.adminRepo = adminRepo;
        this.petugasRepo = petugasRepo;
        this.wargaRepo = wargaRepo;
    }

    /** @throws IllegalArgumentException kalau jenis data atau susunan file tidak sesuai */
    public Hasil importData(String jenis, InputStream file) throws IOException {
        boolean petugas = "Petugas".equals(jenis);
        if (!petugas && !"Admin".equals(jenis)) {
            throw new IllegalArgumentException("Jenis data harus Petugas atau Admin.");
        }
        List<String> kolomWajib = petugas
                ? List.of("nik", "nama_lengkap", "kota_kerja", "password")
                : List.of("nik", "nama_lengkap", "password");

        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            Map<String, Integer> kolom = bacaJudul(sheet.getRow(0), formatter);

            List<String> hilang = kolomWajib.stream().filter(k -> !kolom.containsKey(k)).toList();
            if (!hilang.isEmpty()) {
                throw new IllegalArgumentException("Kolom tidak ditemukan di baris pertama: "
                        + String.join(", ", hilang));
            }

            int berhasil = 0;
            List<String> gagal = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> data = new HashMap<>();
                for (String k : kolomWajib) {
                    data.put(k, formatter.formatCellValue(row.getCell(kolom.get(k))).trim());
                }
                if (data.values().stream().allMatch(String::isEmpty)) continue;

                String salah = periksa(data, row.getCell(kolom.get("nik")), petugas);
                if (salah != null) {
                    gagal.add("Baris " + (i + 1) + ": " + salah);
                    continue;
                }

                if (petugas) {
                    petugasRepo.save(new Petugas(data.get("nik"), data.get("nama_lengkap"),
                            data.get("kota_kerja"), data.get("password")));
                } else {
                    adminRepo.save(new Admin(data.get("nik"), data.get("nama_lengkap"),
                            data.get("password")));
                }
                berhasil++;
            }
            return new Hasil(berhasil, gagal);
        }
    }

    private static Map<String, Integer> bacaJudul(Row judul, DataFormatter formatter) {
        Map<String, Integer> kolom = new HashMap<>();
        if (judul != null) {
            for (Cell cell : judul) {
                kolom.put(formatter.formatCellValue(cell).trim().toLowerCase(Locale.ROOT),
                        cell.getColumnIndex());
            }
        }
        return kolom;
    }

    /** @return alasan baris ditolak, atau null kalau baris valid */
    private String periksa(Map<String, String> data, Cell selNik, boolean petugas) {
        // Excel hanya menyimpan 15 digit angka, jadi NIK bertipe angka sudah tidak akurat.
        if (selNik != null && selNik.getCellType() == CellType.NUMERIC) {
            return "kolom nik harus berformat Text, bukan angka";
        }
        String nik = data.get("nik");
        if (!nik.matches("\\d{16}")) return "NIK harus 16 digit angka";

        String nama = data.get("nama_lengkap");
        if (nama.isEmpty() || nama.length() > 50) return "nama_lengkap wajib diisi, maksimal 50 karakter";

        if (petugas) {
            String kota = data.get("kota_kerja");
            if (kota.isEmpty() || kota.length() > 50) return "kota_kerja wajib diisi, maksimal 50 karakter";
        }

        String password = data.get("password");
        if (password.isEmpty() || password.length() > 10) return "password wajib diisi, maksimal 10 karakter";

        // Login mencari NIK di ketiga tabel, jadi NIK tidak boleh dipakai dua kali.
        if (adminRepo.existsById(nik) || petugasRepo.existsById(nik) || wargaRepo.existsById(nik)) {
            return "NIK " + nik + " sudah terdaftar";
        }
        return null;
    }
}
