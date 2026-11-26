package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.LaporanBencana;
import com.example.demo.model.Warga;
import com.example.demo.repo.Bencana.BencanaRepository;
import com.example.demo.repo.LaporanBencanaRepository;
import com.example.demo.repo.Pengumuman.PengumumanRepository;
import com.example.demo.repo.WargaRepository;

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

    @Autowired
    private LaporanBencanaRepository laporanRepo;

    private final String UPLOAD_DIR = "uploads/";

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        int tahun_sekarang = LocalDate.now().getYear();
        model.addAttribute("name", session.getAttribute("nama"));
        model.addAttribute("daftarBencana", bencanaRepo.rekapBencana(tahun_sekarang));
        model.addAttribute("daftarPengumumanAktif", pengumumanRepo.pengumumanAktif());
        return "Warga/index";
    }

    @GetMapping("instruksi/")
    public String instruksi() {
        return "Warga/instruksi";
    }

    @GetMapping("tambah/")
    public String tambahLaporan(HttpSession session, Model model) {
        model.addAttribute("laporanBencana", new LaporanBencana());
        model.addAttribute("daftarBencana", bencanaRepo.findAll());
        return "Warga/tambah_laporan";
    }

    @PostMapping("laporan/")
    public String kirimLaporan (@ModelAttribute LaporanBencana laporan,
                                HttpSession session,
                                @RequestParam("id_kel") String idkel,
                                @RequestParam("foto") MultipartFile fileFoto) throws IOException {
         
        Path uploadPath = Paths.get("images/");
        String nama_foto = UUID.randomUUID().toString() + "_" + fileFoto.getOriginalFilename();
        Path filePath = uploadPath.resolve(nama_foto);
        Files.copy(fileFoto.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        laporan.setFoto("/uploads/" + nama_foto);

        Warga nik_warga = (Warga) session.getAttribute("nik");
        laporan.setId_warga(nik_warga);

        laporanRepo.save(laporan);

        return "Warga/konfirmasi_laporan";
    }


}
