package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Kecamatan;

public interface KecamatanRepository extends JpaRepository<Kecamatan, String> {
}
