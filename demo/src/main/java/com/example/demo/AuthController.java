package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Admin;
import com.example.demo.model.Petugas;
import com.example.demo.model.Warga;
import com.example.demo.repo.AdminRepository;
import com.example.demo.repo.PetugasRepository;
import com.example.demo.repo.WargaRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_PETUGAS = "PETUGAS";
	public static final String ROLE_WARGA = "WARGA";

	private static final String LOGIN_ERROR = "NIK atau Password salah";

	private final AdminRepository adminRepository;
	private final PetugasRepository petugasRepository;
	private final WargaRepository wargaRepository;

	public AuthController(AdminRepository adminRepository, PetugasRepository petugasRepository,
			WargaRepository wargaRepository) {
		this.adminRepository = adminRepository;
		this.petugasRepository = petugasRepository;
		this.wargaRepository = wargaRepository;
	}

	/** Halaman utama untuk tiap role. */
	public static String dashboardUrl(String role) {
		if (ROLE_ADMIN.equals(role)) {
			return "/admin/dashboard";
		}
		if (ROLE_PETUGAS.equals(role)) {
			return "/petugas/dashboard";
		}
		return "/warga/";
	}

	@GetMapping("/login")
	public String loginForm(HttpSession session, Model model) {
		String role = (String) session.getAttribute("role");
		if (role != null) {
			return "redirect:" + dashboardUrl(role);
		}
		model.addAttribute("loginRequest", new LoginRequest());
		return "General/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginRequest") LoginRequest request, HttpSession session, Model model) {
		String nik = request.getNik() == null ? "" : request.getNik().trim();
		String password = request.getPassword() == null ? "" : request.getPassword();

		// Role ditentukan dari tabel tempat NIK terdaftar: admin, lalu petugas, lalu warga.
		String role = null;
		String nama = null;
		String passwordDb = null;

		Admin admin = adminRepository.findById(nik).orElse(null);
		if (admin != null) {
			role = ROLE_ADMIN;
			nama = admin.getNama_lengkap();
			passwordDb = admin.getPassword();
		} else {
			Petugas petugas = petugasRepository.findById(nik).orElse(null);
			if (petugas != null) {
				role = ROLE_PETUGAS;
				nama = petugas.getNama_lengkap();
				passwordDb = petugas.getPassword();
			} else {
				Warga warga = wargaRepository.findById(nik).orElse(null);
				if (warga != null) {
					role = ROLE_WARGA;
					nama = warga.getNama_lengkap();
					passwordDb = warga.getPassword();
				}
			}
		}

		if (role == null || !password.equals(passwordDb)) {
			model.addAttribute("error", LOGIN_ERROR);
			return "General/login";
		}

		session.setAttribute("nik", nik);
		session.setAttribute("nama", nama);
		session.setAttribute("role", role);
		return "redirect:" + dashboardUrl(role);
	}

	@GetMapping("/register")
	public String registerForm(HttpSession session, Model model) {
		String role = (String) session.getAttribute("role");
		if (role != null) {
			return "redirect:" + dashboardUrl(role);
		}
		model.addAttribute("registerRequest", new RegisterRequest());
		return "General/register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute("registerRequest") RegisterRequest request, Model model) {
		String nik = request.getNik() == null ? "" : request.getNik().trim();
		String nama = request.getNama() == null ? "" : request.getNama().trim();
		String password = request.getPassword() == null ? "" : request.getPassword();

		// Batas panjang mengikuti kolom di database (nama 50, password 10).
		String error = null;
		if (!nik.matches("\\d{16}")) {
			error = "NIK harus 16 digit angka";
		} else if (nama.length() < 3 || nama.length() > 50) {
			error = "Nama lengkap harus 3-50 karakter";
		} else if (password.length() < 8 || password.length() > 10) {
			error = "Password harus 8-10 karakter";
		}
		if (error != null) {
			model.addAttribute("error", error);
			return "General/register";
		}

		// NIK admin dan petugas sudah didaftarkan sebelumnya, jadi tidak bisa dipakai register.
		if (adminRepository.existsById(nik) || petugasRepository.existsById(nik) || wargaRepository.existsById(nik)) {
			model.addAttribute("error", "NIK sudah terdaftar");
			return "General/register";
		}

		Warga warga = new Warga();
		warga.setNik(nik);
		warga.setNama_lengkap(nama);
		warga.setPassword(password);
		wargaRepository.save(warga);

		return "redirect:/login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
