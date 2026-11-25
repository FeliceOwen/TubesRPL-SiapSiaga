
package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repo.LaporanBencanaRepository;
import com.example.demo.repo.RekapLaporanTerverifikasi;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    private final LaporanBencanaRepository laporanBencanaRepository;

    // Constructor untuk menghubungkan Repository
    public AdminController(LaporanBencanaRepository laporanBencanaRepository) {
        this.laporanBencanaRepository = laporanBencanaRepository;
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
}
