package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "warga")

public class Warga {

    @Id
    @Column(columnDefinition = "CHAR(16)")
    private String nik;

    @Column(nullable = false, length = 50)
    private String nama_lengkap;

    @Column(nullable = false, length = 10)
    private String password;

     public Warga() {
    }

    // Getter dan Setter
    public String getNik() {
        return nik;
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
