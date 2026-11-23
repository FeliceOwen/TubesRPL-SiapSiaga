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
@Table(name = "kelurahan")

public class Kelurahan {

    @Id
    @Column(length=3)
    private String id_kel;

    @ManyToOne
    @JoinColumn(name="id_kec", referencedColumnName="id_kec", nullable = false)
    private Kecamatan id_kec;

    @Column(length=30)
    private String nama_kel;    
}
