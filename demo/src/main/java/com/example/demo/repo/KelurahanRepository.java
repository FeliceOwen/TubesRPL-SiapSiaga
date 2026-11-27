package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Kelurahan;

public interface KelurahanRepository extends JpaRepository<Kelurahan, String> {
}
