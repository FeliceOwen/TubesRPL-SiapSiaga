package com.example.demo.repo;

import com.example.demo.model.LogPenanganan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogPenangananRepository extends JpaRepository<LogPenanganan, String> {

    @Query("SELECT l FROM LogPenanganan l WHERE l.id_laporan.id_laporan = :idLaporan ORDER BY l.tanggal_penanganan DESC, l.id_log DESC")
    List<LogPenanganan> findByIdLaporan(@Param("idLaporan") String idLaporan);
}