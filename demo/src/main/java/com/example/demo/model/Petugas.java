package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "petugas")

public class Petugas {

    @Id
    @Column(columnDefinition = "CHAR(16)")
    private String nik;

    @Column(nullable = false, length = 50)
    private String nama_lengkap;

    @Column(nullable = false, length = 50)
    private String kota_kerja;

    @Column(nullable = false, length = 10)
    private String password;
}
