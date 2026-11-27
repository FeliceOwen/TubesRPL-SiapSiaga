
package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Petugas;
import com.example.demo.model.Warga;
import com.example.demo.repo.LaporanBencanaRepository;
import com.example.demo.repo.PetugasRepository;
import com.example.demo.repo.RekapLaporanTerverifikasi;

import jakarta.servlet.http.HttpSession;
import com.example.demo.repo.WargaRepository;


@Controller
public class AdminController {

    private final LaporanBencanaRepository laporanBencanaRepository;
    private final WargaRepository wargaRepository;
    private final PetugasRepository petugasRepository;

    // Constructor untuk menghubungkan Repository
    public AdminController(LaporanBencanaRepository laporanBencanaRepository
                            ,WargaRepository wargaRepository
                            ,PetugasRepository petugasRepository) {
        this.laporanBencanaRepository = laporanBencanaRepository;
        this.wargaRepository = wargaRepository;
        this.petugasRepository = petugasRepository;
    }

    @GetMapping("/admin/laporan-warga")
    public String adminLaporan(HttpSession session, Model model) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login";
        }

        if (!role.equals(AuthController.ROLE_ADMIN)) {
            return "redirect:" + AuthController.dashboardUrl(role);
        }

        List<RekapLaporanTerverifikasi> daftarLaporan =
                laporanBencanaRepository.daftarLaporanTerverifikasi();

        model.addAttribute("daftarLaporan", daftarLaporan);

        return "Admin/laporanWargaVerif";
    }

    @GetMapping("/admin/import-data")
    public String adminImportData() {
        return "Admin/importData";
    }

    @GetMapping("/admin/data-petugas")
    public String adminDataPetugas(Model model) {
        List<Petugas> daftarPetugas = petugasRepository.findAll();
        model.addAttribute("daftarPetugas", daftarPetugas);
        return "Admin/dataPetugas";
    }
    @GetMapping("/admin/data-sekolah")
    public String adminDataSekolah() {
        return "Admin/dataSekolah";
    }
    @GetMapping("/admin/data-warga")
    public String dataWarga(Model model) {
        List<Warga> daftarWarga = wargaRepository.findAll();

        model.addAttribute("daftarWarga", daftarWarga);

        return "Admin/dataWarga";
    }
    @PostMapping("/admin/data-warga/hapus/{nik}")
    public String hapusWarga(@PathVariable String nik) {

        wargaRepository.deleteById(nik);

        return "redirect:/admin/data-warga";
    }
    @GetMapping("/admin/data-wilayah")
    public String adminDataWilayah() {
        return "Admin/dataWilayah";
    }
    @GetMapping("/laporan/detailAdmin/{id}") 
    public String adminDetail() {
        return "Admin/detailBencanaAdmin";
    }

    @GetMapping("/admin/data-petugas/edit/{nik}")
    public String editPetugas(@PathVariable String nik, Model model) {
        Petugas petugas = petugasRepository.findById(nik).orElse(null);

        if (petugas == null) {
            return "redirect:/admin/data-petugas";
        }

        model.addAttribute("petugas", petugas);

        return "Admin/editPetugas";
    }

    @PostMapping("/admin/data-petugas/edit/{nik}")
    public String updatePetugas(@PathVariable String nik,
                            @ModelAttribute Petugas petugas) {

        petugas.setNik(nik);
        petugasRepository.save(petugas);

        return "redirect:/admin/data-petugas";
    }

    @PostMapping("/admin/data-petugas/hapus/{nik}")
    public String hapusPetugas(@PathVariable String nik) {

        petugasRepository.deleteById(nik);

        return "redirect:/admin/data-petugas";
    }

    @GetMapping("/admin/data-petugas/tambah") 
    public String adminTambah() {
        return "Admin/tambahPetugas";
    }
    @PostMapping("/admin/data-petugas/tambah")
    public String tambahPetugas(@ModelAttribute Petugas petugas) {

        petugasRepository.save(petugas);

        return "redirect:/admin/data-petugas";
    }

     @GetMapping("/admin/upload-data") 
    public String adminUpload() {
        return "Admin/uploadData";
    }
}
