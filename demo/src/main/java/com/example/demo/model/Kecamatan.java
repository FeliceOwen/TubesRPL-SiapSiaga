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
@Table(name = "kecamatan")

public class Kecamatan {

    @Id
    @Column(length=20)
    private String id_kec;

    @Column(length=30)
    private String nama_kec;  
    
    @ManyToOne
    @JoinColumn(name="id_kota_kab", referencedColumnName="id_kota_kab", nullable = false)
    private KotaKabupaten id_kota_kab;
}
