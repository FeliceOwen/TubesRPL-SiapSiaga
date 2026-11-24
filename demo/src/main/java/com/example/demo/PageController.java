package com.example.demo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repo.Pengumuman.PengumumanRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

	@Autowired
	private PengumumanRepository pengumumanRepo;

	/** Null kalau role sesuai; kalau tidak, alamat redirect (login atau dashboard role-nya). */
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

	@GetMapping("/petugas/dashboard")
	public String dashboardPetugas(HttpSession session, Model model) {
		String redirect = guard(session, AuthController.ROLE_PETUGAS);
		if (redirect != null) {
			return redirect;
		}
		
		model.addAttribute("statistik", statistikKosong());
		model.addAttribute("daftarPengumuman", pengumumanRepo.pengumumanAktif());
		return "Petugas/DashboardPetugas";
	}

	@GetMapping("/admin/dashboard")
	public String dashboardAdmin(HttpSession session, Model model) {
		String redirect = guard(session, AuthController.ROLE_ADMIN);
		if (redirect != null) {
			return redirect;
		}
		model.addAttribute("statistik", statistikKosong());
		model.addAttribute("daftarPengumuman", pengumumanRepo.pengumumanAktif());
		return "Admin/dashboardAdmin";
	}

	// Placeholder sampai statistik diambil dari tabel laporan_bencana.
	private Map<String, Integer> statistikKosong() {
		Map<String, Integer> statistik = new LinkedHashMap<>();
		for (String jenis : List.of("Kebakaran", "Gempa", "Keracunan Makanan", "Banjir", "Tanah Longsor")) {
			statistik.put(jenis, 0);
		}
		return statistik;
	}
}
