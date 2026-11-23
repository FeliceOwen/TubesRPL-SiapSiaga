package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "kota_kabupaten")

public class KotaKabupaten {

    @Id
    @Column(length=3)
    private String id_kota_kab;

    @ManyToOne
    @JoinColumn(name="id_provinsi", referencedColumnName="id_provinsi", nullable = false)
    private Provinsi id_provinsi;

    @Column(length=30)
    private String nama_kota_kab;    
}
