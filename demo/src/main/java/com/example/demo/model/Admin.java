package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "admin")

public class Admin {

    @Id
    @Column(columnDefinition = "CHAR(16)")
    private String nik;

    @Column(nullable = false, length = 50)
    private String nama_lengkap;

    @Column(nullable = false, length = 10)
    private String password;
}