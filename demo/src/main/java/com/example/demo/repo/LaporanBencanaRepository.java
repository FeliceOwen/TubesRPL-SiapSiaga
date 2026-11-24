package com.example.demo.repo;

import com.example.demo.model.LaporanBencana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaporanBencanaRepository extends JpaRepository<LaporanBencana, String> {

    @Query("SELECT l FROM LaporanBencana l WHERE l.statusHapus = 0 ORDER BY l.tanggalKejadian DESC")
    List<LaporanBencana> findAllActiveLaporan();

    @Query("SELECT COUNT(l) FROM LaporanBencana l WHERE l.statusHapus = 0 AND LOWER(l.id_bencana.nama_bencana) = LOWER(:namaBencana)")
    long countByNamaBencana(@Param("namaBencana") String namaBencana);
}