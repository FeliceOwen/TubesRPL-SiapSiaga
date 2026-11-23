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
@Table(name = "log_penanganan")
public class LogPenanganan {

    @Id
    @Column(length=4)
    private String id_log;

    @ManyToOne
    @JoinColumn(name="id_laporan", referencedColumnName="id_laporan", nullable = false)
    private LaporanBencana id_laporan;

    @ManyToOne
    @JoinColumn(name="nik", referencedColumnName="nik", nullable = true)
    private Warga nik;

    @Column(length=4)
    private int jumlah_personel;

    @Column(length=300)
    private String aktivitas;
    
    @Lob
    @Column(columnDefinition = "VARBINARY(MAX)")
    private byte[] foto_penanganan;
}
