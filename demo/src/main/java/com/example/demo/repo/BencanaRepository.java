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
            bencana.nama_bencana AS namaBencana,
            COUNT(laporan.id_laporan) AS jumlahKejadian
        FROM
            bencana
        LEFT JOIN
            laporan_bencana AS laporan
            ON bencana.id_bencana = laporan.id_bencana
            AND YEAR(laporan.tanggal_kejadian) = :tahun
        GROUP BY
            bencana.id_bencana, bencana.nama_bencana
        """, nativeQuery=true)
        List<RekapBencana> rekapBencana(@Param("tahun") int tahun);
}
