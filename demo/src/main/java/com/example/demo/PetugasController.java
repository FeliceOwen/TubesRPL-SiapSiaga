package com.example.demo;

import com.example.demo.model.LaporanBencana;
import com.example.demo.model.LogPenanganan;
import com.example.demo.model.Pengumuman;
import com.example.demo.model.StatusLaporan;
import com.example.demo.repo.LaporanBencanaRepository;
import com.example.demo.repo.LogPenangananRepository;
import com.example.demo.repo.Pengumuman.PengumumanRepository;
import com.example.demo.repo.StatusLaporanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PetugasController {

    @Autowired
    private LaporanBencanaRepository laporanRepo;

    @Autowired
    private LogPenangananRepository logRepo;

    @Autowired
    private PengumumanRepository pengumumanRepo;

    @Autowired
    private StatusLaporanRepository statusRepo;

    // 1. Daftar Laporan Warga
    @GetMapping("/laporan-warga")
    public String laporanWarga(Model model) {
        List<LaporanBencana> daftarLaporan = laporanRepo.findAllActiveLaporan();
        model.addAttribute("daftarLaporan", daftarLaporan);
        return "Petugas/LaporanWarga";
    }

    // 2. Detail Bencana
    @GetMapping("/laporan/detail/{id}")
    public String detailBencana(@PathVariable("id") String id, Model model) {
        LaporanBencana laporan = laporanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Laporan tidak ditemukan: " + id));

        List<LogPenanganan> daftarLog = logRepo.findByIdLaporan(id);

        model.addAttribute("laporan", laporan);
        model.addAttribute("daftarLog", daftarLog);
        model.addAttribute("logTerakhir", daftarLog.isEmpty() ? null : daftarLog.get(0));

        return "Petugas/DetailBencana";
    }

    // 3. Update Status Verifikasi Laporan
    @PostMapping("/laporan/update-status/{id}")
    public String updateStatusLaporan(@PathVariable("id") String id,
                                      @RequestParam("status") String statusBaru,
                                      RedirectAttributes redirectAttributes) {
        LaporanBencana laporan = laporanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Laporan tidak ditemukan: " + id));

        // Mencari status berdasarkan teks verifikasi atau ID status (ST1/ST2/ST3)
        StatusLaporan statusEntity = statusRepo.findByStatusVerifikasi(statusBaru)
                .orElseGet(() -> statusRepo.findById(statusBaru)
                        .orElseGet(() -> {
                            StatusLaporan baru = new StatusLaporan();
                            baru.setId_status(generateNextStatusId());
                            baru.setStatus_verifikasi(statusBaru);
                            return statusRepo.save(baru);
                        }));

        laporan.setId_status(statusEntity);
        laporanRepo.save(laporan);

        redirectAttributes.addFlashAttribute("pesanSukses", "Status laporan berhasil diperbarui menjadi " + statusEntity.getStatus_verifikasi());
        return "redirect:/laporan/detail/" + id;
    }

    // 4. Form & Simpan Log Penanganan
    @GetMapping("/penanganan/tambah/{id}")
    public String formLogPenanganan(@PathVariable("id") String id, Model model) {
        LaporanBencana laporan = laporanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Laporan tidak ditemukan: " + id));

        model.addAttribute("laporan", laporan);
        return "Petugas/LogPenanganan";
    }

    @PostMapping("/penanganan/simpan/{id}")
    public String simpanLogPenanganan(@PathVariable("id") String id,
                                      @RequestParam("totalPersonel") int totalPersonel,
                                      @RequestParam("aktivitas") String aktivitas,
                                      @RequestParam(value = "foto", required = false) MultipartFile foto,
                                      RedirectAttributes redirectAttributes) throws IOException {

        LaporanBencana laporan = laporanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Laporan tidak ditemukan: " + id));

        LogPenanganan log = new LogPenanganan();
        log.setId_log(generateNextLogId());
        log.setId_laporan(laporan);
        log.setJumlah_personel(totalPersonel);
        log.setAktivitas(aktivitas);
        log.setTanggal_penanganan(LocalDate.now());

        if (foto != null && !foto.isEmpty()) {
            log.setFoto_penanganan(foto.getBytes());
        }

        logRepo.save(log);

        redirectAttributes.addFlashAttribute("pesanSukses", "Log penanganan berhasil ditambahkan!");
        return "redirect:/laporan/detail/" + id;
    }

    // 5. Form Tambah Pengumuman (/pengumuman/tambah/{id})
    @GetMapping("/pengumuman/tambah/{id}")
    public String formTambahPengumuman(@PathVariable("id") String id, Model model) {
        LaporanBencana laporan = laporanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Laporan tidak ditemukan: " + id));

        model.addAttribute("laporan", laporan);
        return "Petugas/TambahPengumuman";
    }

    // Simpan Pengumuman Baru (/pengumuman/simpan)
    @PostMapping("/pengumuman/simpan")
    public String simpanPengumuman(@RequestParam("idLaporan") String idLaporan,
                                   @RequestParam("detailLokasi") String detailLokasi,
                                   @RequestParam("catatanPetugas") String catatanPetugas,
                                   RedirectAttributes redirectAttributes) {
        LaporanBencana laporan = laporanRepo.findById(idLaporan)
                .orElseThrow(() -> new IllegalArgumentException("Laporan tidak ditemukan: " + idLaporan));

        // 1. Update status jadi ST2 & isi catatan agar terbaca query pengumumanAktif()
        statusRepo.findById("ST2").ifPresent(laporan::setId_status);
        laporan.setCatatan(catatanPetugas);
        laporanRepo.save(laporan);

        // 2. Simpan ke tabel pengumuman
        Pengumuman pengumuman = new Pengumuman();
        pengumuman.setId_pengumuman(generateNextPengumumanId());
        pengumuman.setId_laporan(laporan);
        pengumuman.setDetail_lokasi(detailLokasi);
        pengumuman.setIsi_pengumuman(catatanPetugas);

        pengumumanRepo.save(pengumuman);
        redirectAttributes.addFlashAttribute("pesanSukses", "Pengumuman berhasil diterbitkan!");
        return "redirect:/petugas/dashboard";
    }

    // Form Edit Pengumuman (/pengumuman/edit/{id})
    @GetMapping("/pengumuman/edit/{id}")
    public String formEditPengumuman(@PathVariable("id") String id, Model model) {
        Pengumuman pengumuman = pengumumanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pengumuman tidak ditemukan: " + id));
        model.addAttribute("pengumuman", pengumuman);
        model.addAttribute("laporan", pengumuman.getId_laporan());
        return "Petugas/EditPengumuman";
    }

    // Update Pengumuman (/pengumuman/update/{id})
    @PostMapping("/pengumuman/update/{id}")
    public String updatePengumuman(@PathVariable("id") String id,
                                   @RequestParam("detailLokasi") String detailLokasi,
                                   @RequestParam("catatanPetugas") String catatanPetugas,
                                   RedirectAttributes redirectAttributes) {
        Pengumuman pengumuman = pengumumanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pengumuman tidak ditemukan: " + id));

        pengumuman.setDetail_lokasi(detailLokasi);
        pengumuman.setIsi_pengumuman(catatanPetugas);
        pengumumanRepo.save(pengumuman);

        redirectAttributes.addFlashAttribute("pesanSukses", "Pengumuman berhasil diperbarui!");
        return "redirect:/petugas/dashboard";
    }

    // 6. Endpoint Render Foto dari Database
    @GetMapping("/laporan/foto/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> tampilkanFotoLaporan(@PathVariable("id") String id) {
        LaporanBencana laporan = laporanRepo.findById(id).orElse(null);
        if (laporan == null || laporan.getFoto() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(laporan.getFoto());
    }

    @GetMapping("/penanganan/foto/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> tampilkanFotoPenanganan(@PathVariable("id") String id) {
        LogPenanganan log = logRepo.findById(id).orElse(null);
        if (log == null || log.getFoto_penanganan() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(log.getFoto_penanganan());
    }

    // Generator ID sesuai panjang kolom di database
    private String generateNextStatusId() {
        long count = statusRepo.count() + 1;
        return String.format("ST%d", count); // 3 karakter: ST1, ST2, ST3[cite: 1]
    }

    private String generateNextLogId() {
        long count = logRepo.count() + 1;
        return String.format("L%03d", count); // 4 karakter: L001, L002, dst.[cite: 2]
    }

    private String generateNextPengumumanId() {
        long count = pengumumanRepo.count() + 1;
        return String.format("P%02d", count); // 3 karakter: P01, P02, dst.
    }
}