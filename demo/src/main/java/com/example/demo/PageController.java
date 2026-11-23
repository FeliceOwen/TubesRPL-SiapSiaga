package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/")
	public String home() {
		return "Warga/index";
	}

	@GetMapping("/laporan/tambah")
	public String tambahLaporan() {
		return "Warga/tambah_laporan";
	}

}
