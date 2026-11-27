package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Provinsi;

public interface ProvinsiRepository extends JpaRepository<Provinsi, String> {
}
