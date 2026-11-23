package com.example.demo.repo;

import com.example.demo.model.Warga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.demo.model.Bencana;

@Repository
public interface BencanaRepository extends JpaRepository<Warga, String> {
    @Query (value = """
        SELECT
            bencana.nama_bencana,
            COUNT(laporan.id) AS jumlah_kejadian
        FROM 
            bencana
        LEFT JOIN
            laporanbencana as laporan
            ON bencana.id_bencana = laporan.id_bencana
        WHERE 
            YEAR(laporan.tanggalKejadian) = :tahun
        GROUP BY
            bencana.id
        """, nativeQuery=true)
        List<RekapBencana> rekapBencana(@Param("tahun_sekarang") int tahun);
}
