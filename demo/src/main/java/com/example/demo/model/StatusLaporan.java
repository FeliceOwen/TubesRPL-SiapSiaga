package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "status_laporan")
public class StatusLaporan {

    @Id
    @Column(length=3)
    private String id_status;

    @Column(length=30)
    private String status_verifikasi;
}
