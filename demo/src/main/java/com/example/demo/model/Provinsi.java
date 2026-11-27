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
@Table(name = "provinsi")

public class Provinsi {

    @Id
    @Column(length=20)
    private String id_provinsi;

    @Column(nullable = false, length = 50, unique = true)
    private String nama_provinsi;
}
