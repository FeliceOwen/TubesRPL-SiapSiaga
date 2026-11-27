// package com.example.demo;

// import com.example.demo.model.Kelurahan;
// import com.example.demo.model.LaporanBencana;
// import com.example.demo.model.LogPenanganan;
// import com.example.demo.model.Pengumuman;
// import com.example.demo.model.StatusLaporan;
// import com.example.demo.repo.LaporanBencanaRepository;
// import com.example.demo.repo.LogPenangananRepository;
// import com.example.demo.repo.Pengumuman.PengumumanRepository;

// import jakarta.servlet.http.HttpSession;

// import com.example.demo.repo.StatusLaporanRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import java.io.IOException;
// import java.time.LocalDate;
// import java.util.List;

// import jakarta.persistence.EntityManager;
// import jakarta.persistence.PersistenceContext;

// @Controller
// public class PetugasController {

//     @Autowired
//     private LaporanBencanaRepository laporanRepo;

//     @Autowired
//     private LogPenangananRepository logRepo;

//     @Autowired
//     private PengumumanRepository pengumumanRepo;

//     @Autowired
//     private StatusLaporanRepository statusRepo;

//     @PersistenceContext
//     private EntityManager entityManager;

//     // 1. Daftar Laporan Warga
//     @GetMapping("/laporan-warga")
//     public String laporanWarga(Model model) {
//         List<LaporanBencana> daftarLaporan = laporanRepo.findAll();

//         // Urutan prioritas status: semakin kecil angka = semakin di atas
//         java.util.Map<String, Integer> prioritasStatus = java.util.Map.of(
//                 "ST1", 1, // Menunggu Verifikasi
//                 "ST2", 2, // Sedang Diproses / Ditangani
//                 "ST3", 3,
//                 "ST4", 4 // Selesai
//         );

//         daftarLaporan.sort((a, b) -> {
//             String idStatusA = (a.getId_status() != null && a.getId_status().getId_status() != null)
//                     ? a.getId_status().getId_status().trim()
//                     : "";
//             String idStatusB = (b.getId_status() != null && b.getId_status().getId_status() != null)
//                     ? b.getId_status().getId_status().trim()
//                     : "";

//             int rankA = prioritasStatus.getOrDefault(idStatusA, 99);
//             int rankB = prioritasStatus.getOrDefault(idStatusB, 99);

//             if (rankA != rankB) {
//                 return Integer.compare(rankA, rankB);
//             }

//             String idA = a.getId_laporan() != null ? a.getId_laporan() : "";
//             String idB = b.getId_laporan() != null ? b.getId_laporan() : "";
//             return idA.compareTo(idB);
//         });

//         @SuppressWarnings("unchecked")
//         List<Object[]> rowsLokasi = entityManager.createNativeQuery("""
//                 SELECT 
//                     laporan.id_laporan,
//                     CONCAT_WS(', ', kelurahan.nama_kel, kota_kabupaten.nama_kota_kab, provinsi.nama_provinsi)
//                 FROM laporan_bencana AS laporan
//                 JOIN kelurahan ON kelurahan.id_kel = laporan.id_kel
//                 JOIN kecamatan ON kecamatan.id_kec = kelurahan.id_kec
//                 JOIN kota_kabupaten ON kota_kabupaten.id_kota_kab = kecamatan.id_kota_kab
//                 JOIN provinsi ON provinsi.id_provinsi = kota_kabupaten.id_provinsi
//                 """)
//                 .getResultList();

//         java.util.Map<String, String> lokasiMap = new java.util.HashMap<>();
//         for (Object[] row : rowsLokasi) {
//             if (row[0] != null && row[1] != null) {
//                 lokasiMap.put(row[0].toString(), row[1].toString());
//             }
//         }

//         model.addAttribute("daftarLaporan", daftarLaporan);
//         model.addAttribute("lokasiMap", lokasiMap);
//         return "Petugas/LaporanWarga";
//     }

//     // 2. Detail Bencana
//     @GetMapping("/laporan/detail/{id}")
//     public String detailLaporan(@PathVariable("id") String id, Model model) {
//         LaporanBencana laporan = laporanRepo.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Laporan tidak ditemukan: " + id));

//         Kelurahan lokasiLengkap = laporan.getId_kel() != null ? laporan.getId_kel() : null;

//         @SuppressWarnings("unchecked")
//         List<Object> hasilLokasi = entityManager.createNativeQuery("""
//                 SELECT CONCAT_WS(', ', kelurahan.nama_kel, kecamatan.nama_kec, kota_kabupaten.nama_kota_kab, provinsi.nama_provinsi)
//                 FROM laporan_bencana AS laporan
//                 JOIN kelurahan ON kelurahan.id_kel = laporan.id_kel
//                 JOIN kecamatan ON kecamatan.id_kec = kelurahan.id_kec
//                 JOIN kota_kabupaten ON kota_kabupaten.id_kota_kab = kecamatan.id_kota_kab
//                 JOIN provinsi ON provinsi.id_provinsi = kota_kabupaten.id_provinsi
//                 WHERE laporan.id_laporan = :idLaporan
//                 """)
//                 .setParameter("idLaporan", id)
//                 .getResultList();

//         if (!hasilLokasi.isEmpty() && hasilLokasi.get(0) != null) {
//             lokasiLengkap = hasilLokasi.get(0).toString();
//         }

//         model.addAttribute("laporan", laporan);
//         model.addAttribute("lokasiLengkap", lokasiLengkap);
//         return "Petugas/DetailBencana";
//     }

//     // 3. Update Status Verifikasi Laporan
//     @PostMapping("/laporan/update-status/{id}")
//     public String updateStatusLaporan(@PathVariable("id") String id,
//             @RequestParam("status") String idStatusBaru,
//             RedirectAttributes redirectAttributes) {
//         LaporanBencana laporan = laporanRepo.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("ID Laporan tidak ditemukan: " + id));

//         // Langsung cari berdasarkan ID status (ST1, ST2, ST3, ST4)
//         StatusLaporan statusEntity = statusRepo.findById(idStatusBaru)
//                 .orElseThrow(() -> new IllegalArgumentException("Status tidak ditemukan: " + idStatusBaru));

//         laporan.setId_status(statusEntity);
//         laporanRepo.save(laporan);

//         redirectAttributes.addFlashAttribute("judul", "Status Laporan Berhasil Diperbarui");
//         return "redirect:/petugas/berhasil";
//     }

//     // Halaman berhasil setelah membuat/mengedit pengumuman atau memperbarui status
//     @GetMapping("/petugas/berhasil")
//     public String halamanBerhasil(Model model) {
//         // judul hanya ada tepat setelah aksi berhasil
//         if (!model.containsAttribute("judul")) {
//             return "redirect:/petugas/dashboard";
//         }
//         return "Petugas/Berhasil";
//     }

//     // 4. Form & Simpan Log Penanganan
//     @GetMapping("/penanganan/tambah/{id}")
//     public String formLogPenanganan(@PathVariable("id") String id, Model model) {
//         LaporanBencana laporan = laporanRepo.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("ID Laporan tidak ditemukan: " + id));

//         model.addAttribute("laporan", laporan);
//         return "Petugas/LogPenanganan";
//     }

//     @PostMapping("/penanganan/simpan/{id}")
//     public String simpanLogPenanganan(@PathVariable("id") String id,
//             @RequestParam("totalPersonel") int totalPersonel,
//             @RequestParam("aktivitas") String aktivitas,
//             @RequestParam(value = "foto", required = false) MultipartFile foto,
//             RedirectAttributes redirectAttributes) throws IOException {

//         LaporanBencana laporan = laporanRepo.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("ID Laporan tidak ditemukan: " + id));

//         LogPenanganan log = new LogPenanganan();
//         log.setId_log(generateNextLogId());
//         log.setId_laporan(laporan);
//         log.setJumlah_personel(totalPersonel);
//         log.setAktivitas(aktivitas);
//         log.setTanggal_penanganan(LocalDate.now());

//         if (foto != null && !foto.isEmpty()) {
//             log.setFoto_penanganan(foto.getBytes());
//         }

//         logRepo.save(log);

//         redirectAttributes.addFlashAttribute("pesanSukses", "Log penanganan berhasil ditambahkan!");
//         return "redirect:/laporan/detail/" + id;
//     }

//     // 5. Form Tambah Pengumuman (/pengumuman/tambah/{id})
//     @GetMapping("/pengumuman/tambah/{id}")
//     public String formTambahPengumuman(@PathVariable("id") String id, Model model) {
//         LaporanBencana laporan = laporanRepo.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("ID Laporan tidak ditemukan: " + id));

//         model.addAttribute("laporan", laporan);
//         return "Petugas/TambahPengumuman";
//     }

//     // Simpan Pengumuman Baru (/pengumuman/simpan)
//     @PostMapping("/pengumuman/simpan")
//     public String simpanPengumuman(@RequestParam("idLaporan") String idLaporan,
//             @RequestParam("detailLokasi") String detailLokasi,
//             @RequestParam("catatanPetugas") String catatanPetugas,
//             RedirectAttributes redirectAttributes) {
//         LaporanBencana laporan = laporanRepo.findById(idLaporan)
//                 .orElseThrow(() -> new IllegalArgumentException("Laporan tidak ditemukan: " + idLaporan));

//         // 1. Update status jadi ST2 & isi catatan agar terbaca query pengumumanAktif()
//         statusRepo.findById("ST2").ifPresent(laporan::setId_status);
//         laporan.setCatatan(catatanPetugas);
//         laporanRepo.save(laporan);

//         // 2. Simpan ke tabel pengumuman
//         Pengumuman pengumuman = new Pengumuman();
//         pengumuman.setId_pengumuman(generateNextPengumumanId());
//         pengumuman.setId_laporan(laporan);
//         pengumuman.setDetail_lokasi(detailLokasi);
//         pengumuman.setIsi_pengumuman(catatanPetugas);

//         pengumumanRepo.save(pengumuman);
//         redirectAttributes.addFlashAttribute("judul", "Pengumuman Berhasil Dibuat");
//         return "redirect:/petugas/berhasil";
//     }

//     // Form Edit Pengumuman (/pengumuman/edit/{id})
//     // Form Edit Pengumuman (/pengumuman/edit/{id})
//     @GetMapping("/pengumuman/edit/{id}")
//     public String formEditPengumuman(@PathVariable("id") String id, Model model) {
//         Pengumuman pengumuman = pengumumanRepo.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Pengumuman tidak ditemukan: " + id));

//         LaporanBencana laporan = pengumuman.getId_laporan();
//         String namaProvinsi = "-";
//         String namaKotaKab = "-";

//         @SuppressWarnings("unchecked")
//         List<Object[]> hasilWilayah = entityManager.createNativeQuery("""
//                 SELECT provinsi.nama_provinsi, kota_kabupaten.nama_kota_kab
//                 FROM pengumuman
//                 JOIN laporan_bencana AS laporan ON pengumuman.id_laporan = laporan.id_laporan
//                 JOIN kelurahan ON kelurahan.id_kel = laporan.id_kel
//                 JOIN kecamatan ON kecamatan.id_kec = kelurahan.id_kec
//                 JOIN kota_kabupaten ON kota_kabupaten.id_kota_kab = kecamatan.id_kota_kab
//                 JOIN provinsi ON provinsi.id_provinsi = kota_kabupaten.id_provinsi
//                 WHERE pengumuman.id_pengumuman = :idPengumuman
//                 """)
//                 .setParameter("idPengumuman", id)
//                 .getResultList();

//         if (!hasilWilayah.isEmpty()) {
//             Object[] row = hasilWilayah.get(0);
//             if (row[0] != null) {
//                 namaProvinsi = row[0].toString();
//             }
//             if (row[1] != null) {
//                 namaKotaKab = row[1].toString();
//             }
//         }

//         model.addAttribute("pengumuman", pengumuman);
//         model.addAttribute("laporan", laporan);
//         model.addAttribute("namaProvinsi", namaProvinsi);
//         model.addAttribute("namaKotaKab", namaKotaKab);
//         return "Petugas/EditPengumuman";
//     }

//     // Update Pengumuman (/pengumuman/update/{id})
//     // Update Pengumuman (/pengumuman/update/{id})
//     @PostMapping("/pengumuman/update/{id}")
//     public String updatePengumuman(@PathVariable("id") String id,
//             @RequestParam("detailLokasi") String detailLokasi,
//             @RequestParam("catatanPetugas") String catatanPetugas,
//             RedirectAttributes redirectAttributes) {
//         Pengumuman pengumuman = pengumumanRepo.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Pengumuman tidak ditemukan: " + id));

//         pengumuman.setDetail_lokasi(detailLokasi);
//         pengumuman.setIsi_pengumuman(catatanPetugas);
//         pengumumanRepo.save(pengumuman);

//         // Sinkronkan juga ke catatan laporan agar langsung tampil di kartu dashboard
//         LaporanBencana laporan = pengumuman.getId_laporan();
//         if (laporan != null) {
//             laporan.setCatatan(catatanPetugas);
//             laporanRepo.save(laporan);
//         }

//         redirectAttributes.addFlashAttribute("judul", "Pengumuman Berhasil Diedit");
//         return "redirect:/petugas/berhasil";
//     }

//     // // 6. Endpoint Render Foto dari Database
//     // @GetMapping("/laporan/foto/{id}")
//     // @ResponseBody
//     // public ResponseEntity<byte[]> tampilkanFotoLaporan(@PathVariable("id") String id) {
//     //     LaporanBencana laporan = laporanRepo.findById(id).orElse(null);
//     //     if (laporan == null || laporan.getFoto() == null) {
//     //         return ResponseEntity.notFound().build();
//     //     }
//     //     return ResponseEntity.ok()
//     //             .contentType(MediaType.IMAGE_JPEG)
//     //             .body(laporan.getFoto());
//     // }
//     @GetMapping("/penanganan/foto/{id}")
//     @ResponseBody
//     public ResponseEntity<byte[]> tampilkanFotoPenanganan(@PathVariable("id") String id) {
//         LogPenanganan log = logRepo.findById(id).orElse(null);
//         if (log == null || log.getFoto_penanganan() == null) {
//             return ResponseEntity.notFound().build();
//         }
//         return ResponseEntity.ok()
//                 .contentType(MediaType.IMAGE_JPEG)
//                 .body(log.getFoto_penanganan());
//     }

//     // Generator ID sesuai panjang kolom di database
//     private String generateNextStatusId() {
//         long count = statusRepo.count() + 1;
//         return String.format("ST%d", count); // 3 karakter: ST1, ST2, ST3[cite: 1]
//     }

//     private String generateNextLogId() {
//         long count = logRepo.count() + 1;
//         return String.format("L%03d", count); // 4 karakter: L001, L002, dst.[cite: 2]
//     }

//     private String generateNextPengumumanId() {
//         long count = pengumumanRepo.count() + 1;
//         return String.format("P%02d", count); // 3 karakter: P01, P02, dst.
//     }
// }
