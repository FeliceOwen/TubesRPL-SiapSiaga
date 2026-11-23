package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "entitas_pengumuman")
public class EntitasPengumuman {

    @Id
    @Column(length=3)
    private String id_pengumuman;

    @ManyToOne
    @JoinColumn(name="id_laporan", referencedColumnName="id_laporan", nullable = false)
    private LaporanBencana id_laporan;

    @ManyToOne
    @JoinColumn(name="nik", referencedColumnName="nik", nullable = true)
    private Warga nik;

    @Column(length=300)
    private String isi_pengumuman;

    @Column(length=100)
    private String detail_lokasi;
}
