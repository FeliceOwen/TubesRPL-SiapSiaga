package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "bencana")
public class Bencana {

    @Id
    @Column(length=3)
    private String id_bencana;

    @Column(length=30)
    private String nama_bencana;
}
