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
import com.example.demo.model.StatusLaporan;
import com.example.demo.model.Bencana;
import com.example.demo.model.Kelurahan;
import com.example.demo.model.Sekolah;
import com.example.demo.model.Warga;
import com.example.demo.repo.Bencana.BencanaRepository;
import com.example.demo.repo.SekolahRepository;
//import com.example.demo.repo.KelurahanRepository;
import com.example.demo.repo.LaporanBencanaRepository;
import com.example.demo.repo.Pengumuman.PengumumanRepository;
import com.example.demo.repo.StatusLaporanRepository;
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
    private StatusLaporanRepository statusRepo;

    @Autowired
    private LaporanBencanaRepository laporanRepo;

    @Autowired
    private SekolahRepository sekolahRepo;

    // @Autowired
    // private KelurahanRepository kelurahanRepo;
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
    public String kirimLaporan(@ModelAttribute LaporanBencana laporan,
            HttpSession session,
            @RequestParam("id_bencana") String idbencana,
            @RequestParam(value = "id_sekolah", required = false) String idsekolah,
            @RequestParam("fotofile") MultipartFile fileFoto) throws IOException {

        // input NIK sebagai id pelapor
        String nik = (String) session.getAttribute("nik");
        if (nik == null) {
            session.invalidate();
            return "redirect:/login";
        }
        Warga warga_pelapor = wargaRepo.findById(nik).orElse(null);
        if (warga_pelapor == null) {
            session.invalidate();
            return "redirect:/login";
        }
        laporan.setId_warga(warga_pelapor);

        // // if(kelurahanRepo.findById(idkelurahan)==null) {
        // //     Kelurahan kelurahan = new Kelurahan();
        // //     kelurahan.setId_kel(idkelurahan);
        // //     kelurahan.setId_kec(idkecamatan);
        // //     kelurahan.setNama_kel(namakelurahan);
        // //     kelurahanRepo.save(kelurahan);
        // // }
        // // Kelurahan kelurahan_kejadian = kelurahanRepo.findById(idkelurahan).orElse(null);
        // laporan.setId_kel(idkelurahan);
        // input file foto
        if (!fileFoto.isEmpty()) {
            Path uploadPath = Paths.get("images/");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String nama_foto = UUID.randomUUID().toString() + "_" + fileFoto.getOriginalFilename();
            Path filePath = uploadPath.resolve(nama_foto);
            Files.copy(fileFoto.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            laporan.setFoto("/images/" + nama_foto);
        }

        // input id laporan
        long id_laporan = laporanRepo.count() + 1;
        laporan.setId_laporan(id_laporan + "");

        // input status laporan
        StatusLaporan defaultStatus = statusRepo.findByStatusVerifikasi("Menunggu Verifikasi").orElse(null);
        laporan.setId_status(defaultStatus);

        //input id bencana
        Bencana bencana = bencanaRepo.findById(idbencana.trim()).orElse(null);
        laporan.setId_bencana(bencana);

        //input id sekolah
        if (idsekolah != null && !idsekolah.isEmpty()) {
            Sekolah sekolah = sekolahRepo.findById(idsekolah.trim()).orElse(null);
            laporan.setId_sekolah(sekolah);
        } else {
            laporan.setId_sekolah(null);
        }

        laporanRepo.save(laporan);

        return "Warga/konfirmasi_laporan";
    }

    @GetMapping("laporansaya/")
    public String tampilkanLaporan(HttpSession session, Model model) {
        String id_pelapor = (String) session.getAttribute("nik");
        return "Warga/laporan";
    }

}
