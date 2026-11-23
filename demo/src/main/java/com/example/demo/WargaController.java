package com.example.demo;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repo.WargaRepository;
import com.example.demo.repo.BencanaRepository;
import com.example.demo.model.Warga;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/warga")

public class WargaController {

    @Autowired
    private WargaRepository wargaRepo;

    @Autowired
    private BencanaRepository bencanaRepo;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
		int tahun_sekarang = LocalDate.now().getYear();
        model.addAttribute("name", session.getAttribute("nama"));
        model.addAttribute("daftarBencana", bencanaRepo.rekapBencana(tahun_sekarang));
        return "Warga/index";
    }

    @GetMapping("/laporan/tambah")
    public String tambahLaporan() {
        return "Warga/tambah_laporan";
    }

}
