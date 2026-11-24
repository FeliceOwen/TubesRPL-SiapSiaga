package com.example.demo;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repo.BencanaRepository;
import com.example.demo.repo.WargaRepository;
import com.example.demo.repo.PengumumanRepository;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/warga")

public class WargaController {

    @Autowired
    private WargaRepository wargaRepo;

    @Autowired
    private BencanaRepository bencanaRepo;

    @Autowired
    private PengumumanRepository pengumumanRepo;

    

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
		int tahun_sekarang = LocalDate.now().getYear();
        model.addAttribute("name", session.getAttribute("nama"));
        model.addAttribute("daftarBencana", bencanaRepo.rekapBencana(tahun_sekarang));
        model.addAttribute("daftarPengumumanAktif", pengumumanRepo.pengumumanAktif());
        return "Warga/index";
    }

    @GetMapping("/laporan/tambah")
    public String tambahLaporan() {
        return "Warga/tambah_laporan";
    }

}
