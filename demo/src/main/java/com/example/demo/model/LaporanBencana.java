package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "laporan_bencana")
public class LaporanBencana {

    @Id
    @Column(length=3)
    private String id_laporan;

    @ManyToOne
    @JoinColumn(name="id_kel", referencedColumnName="id_kel", nullable = false)
    private Kelurahan id_kel;

    @ManyToOne
    @JoinColumn(name="id_sekolah", referencedColumnName="id_sekolah", nullable = true)
    private Sekolah id_sekolah;

    @ManyToOne
    @JoinColumn(name="id_status", referencedColumnName="id_status", nullable = false)
    private StatusLaporan id_status;

    @ManyToOne
    @JoinColumn(name="id_bencana", referencedColumnName="id_bencana", nullable = false)
    private Bencana id_bencana;

    @ManyToOne
    @JoinColumn(name="id_warga", referencedColumnName="nik", nullable = false)
    private Warga id_warga;

    @Column(length=300)
    private String catatan;
    
    @Lob
    @Column(columnDefinition = "VARBINARY(MAX)")
    private byte[] foto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalKejadian;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime jamKejadian;

    @Column(length = 1)
    private int statusHapus;
}
