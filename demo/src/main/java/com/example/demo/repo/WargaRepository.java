package com.example.demo.repo;

import com.example.demo.model.Warga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WargaRepository extends JpaRepository<Warga, String> {
}
