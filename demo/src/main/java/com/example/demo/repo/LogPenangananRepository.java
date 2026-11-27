package com.example.demo.repo;

import com.example.demo.model.LogPenanganan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface LogPenangananRepository extends JpaRepository<LogPenanganan, String> {
    @Query(value = "SELECT * FROM log_penanganan WHERE id_laporan = :id", nativeQuery = true)
    List<LogPenanganan> findByIdLaporan(@Param("id") String id);
}