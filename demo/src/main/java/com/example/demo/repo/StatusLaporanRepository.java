package com.example.demo.repo;

import com.example.demo.model.StatusLaporan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusLaporanRepository extends JpaRepository<StatusLaporan, String> {

    @Query("SELECT s FROM StatusLaporan s WHERE LOWER(s.status_verifikasi) = LOWER(:status)")
    Optional<StatusLaporan> findByStatusVerifikasi(@Param("status") String status);
}