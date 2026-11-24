package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "pengumuman")
public class Pengumuman {

    @Id
    @Column(length = 3)
    private String id_pengumuman;

    @ManyToOne
    @JoinColumn(name = "id_laporan", referencedColumnName = "id_laporan", nullable = false)
    private LaporanBencana id_laporan;

    @ManyToOne
    @JoinColumn(name = "nik", referencedColumnName = "nik", nullable = true)
    private Warga nik;

    @Column(length = 300)
    private String isi_pengumuman;

    @Column(length = 100)
    private String detail_lokasi;
}
