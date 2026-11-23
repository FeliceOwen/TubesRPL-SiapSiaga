package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "provinsi")

public class Provinsi {

    @Id
    @Column(length=5)
    private String id_provinsi;

    @Column(nullable = false, length = 50, unique = true)
    private String nama_provinsi;

    public Provinsi() {
    }

    // Getter dan Setter
    public String getId() {
        return id_provinsi;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
