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
@Table(name = "sekolah")
public class Sekolah {

    @Id
    @Column(length = 20)
    private String id_sekolah;

    @Column(length = 30)
    private String nama_sekolah;

    @ManyToOne
    @JoinColumn(name = "id_kel", referencedColumnName = "id_kel", nullable = false)
    private Kelurahan id_kel;
}
