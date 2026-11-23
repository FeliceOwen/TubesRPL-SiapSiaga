package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Petugas;

public interface PetugasRepository extends JpaRepository<Petugas, String> {
}
