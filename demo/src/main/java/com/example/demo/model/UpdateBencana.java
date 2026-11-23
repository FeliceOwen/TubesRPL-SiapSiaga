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
@Table(name = "update_bencana")
public class UpdateBencana {

    @Id
    @Column(length=4)
    private String id_update;

    @ManyToOne
    @JoinColumn(name="id_laporan", referencedColumnName="id_laporan", nullable = false)
    private LaporanBencana id_laporan;

    @ManyToOne
    @JoinColumn(name="nik", referencedColumnName="nik", nullable = true)
    private Warga nik;

    @ManyToOne
    @JoinColumn(name="id_status", referencedColumnName="id_status", nullable = false)
    private StatusLaporan id_status;

    @Column(length=300)
    private String detail_update;
    
    @Lob
    @Column(columnDefinition = "VARBINARY(MAX)")
    private byte[] foto_terbaru;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggal_update;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime jam_update;
}
