package com.example.demo;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import com.example.demo.repo.Bencana.BencanaRepository;
import com.example.demo.repo.Pengumuman.PengumumanRepository;
import com.example.demo.repo.Bencana.RekapBencana;
import com.example.demo.repo.Pengumuman.RekapPengumuman;

@Controller
public class PageController {

    private final BencanaRepository bencanaRepository;
    private final PengumumanRepository pengumumanRepository;

    public PageController(
            BencanaRepository bencanaRepository,
            PengumumanRepository pengumumanRepository) {

        this.bencanaRepository = bencanaRepository;
        this.pengumumanRepository = pengumumanRepository;
    }

    /** Mengecek role pengguna */
    private String guard(HttpSession session, String requiredRole) {
        String role = (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login";
        }

        if (!role.equals(requiredRole)) {
            return "redirect:" + AuthController.dashboardUrl(role);
        }

        return null;
    }

    // Profil Warga
    @GetMapping("/profil")
    public String profil(HttpSession session, Model model) {
        String redirect = guard(session, AuthController.ROLE_WARGA);

        if (redirect != null) {
            return redirect;
        }

        model.addAttribute("nik", session.getAttribute("nik"));
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("role", "Warga");

        return "Warga/profil";
    }

    // Profil Petugas
    @GetMapping("/petugas/profil")
    public String profilPetugas(HttpSession session, Model model) {
        String redirect = guard(session, AuthController.ROLE_PETUGAS);

        if (redirect != null) {
            return redirect;
        }

        model.addAttribute("nik", session.getAttribute("nik"));
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("role", "Petugas");

        return "Petugas/ProfilPetugas";
    }

    // Profil Admin
    @GetMapping("/admin/profil")
    public String profilAdmin(HttpSession session, Model model) {
        String redirect = guard(session, AuthController.ROLE_ADMIN);

        if (redirect != null) {
            return redirect;
        }

        model.addAttribute("nik", session.getAttribute("nik"));
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("role", "Admin");

        return "Admin/profilAdmin";
    }

    // Dashboard Petugas
    @GetMapping("/petugas/dashboard")
    public String dashboardPetugas(HttpSession session, Model model) {
        String redirect = guard(session, AuthController.ROLE_PETUGAS);

        if (redirect != null) {
            return redirect;
        }

        model.addAttribute("statistik", statistikKosong());

        List<RekapPengumuman> daftarPengumuman =
                pengumumanRepository.pengumumanAktif();

        model.addAttribute("daftarPengumuman", daftarPengumuman);

        return "Petugas/DashboardPetugas";
    }

    // Dashboard Admin
    @GetMapping("/admin/dashboard")
    public String dashboardAdmin(HttpSession session, Model model) {
        String redirect = guard(session, AuthController.ROLE_ADMIN);

        if (redirect != null) {
            return redirect;
        }

        // Mengambil statistik bencana tahun ini
        int tahun = LocalDate.now().getYear();

        List<RekapBencana> hasilStatistik =
                bencanaRepository.rekapBencana(tahun);

        Map<String, Integer> statistik = statistikKosong();

        for (RekapBencana data : hasilStatistik) {
            statistik.put(
                    data.getNamaBencana(),
                    data.getJumlahKejadian()
            );
        }

        model.addAttribute("statistik", statistik);

        // Mengambil pengumuman aktif
        List<RekapPengumuman> daftarPengumuman =
                pengumumanRepository.pengumumanAktif();

        model.addAttribute("daftarPengumuman", daftarPengumuman);

        model.addAttribute("nik", session.getAttribute("nik"));
        model.addAttribute("nama", session.getAttribute("nama"));

        return "Admin/dashboardAdmin";
    }

    // Statistik default
    private Map<String, Integer> statistikKosong() {
        Map<String, Integer> statistik = new LinkedHashMap<>();

        for (String jenis : List.of(
                "Kebakaran",
                "Gempa",
                "Keracunan Makanan",
                "Banjir",
                "Tanah Longsor"
        )) {
            statistik.put(jenis, 0);
        }

        return statistik;
    }

}