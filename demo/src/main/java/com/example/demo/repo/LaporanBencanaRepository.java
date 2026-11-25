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

    @Query(value = """
        SELECT
            laporan.id_laporan AS id,
            bencana.nama_bencana AS jenisBencana,
            CONCAT_WS(', ',
                kelurahan.nama_kel,
                kecamatan.nama_kec,
                kota_kabupaten.nama_kota_kab,
                provinsi.nama_provinsi
            ) AS lokasi,
            laporan.tanggal_kejadian AS tanggal,
            laporan.jam_kejadian AS waktu,
            laporan.catatan AS catatan
        FROM laporan_bencana AS laporan
        JOIN bencana
            ON bencana.id_bencana = laporan.id_bencana
        JOIN kelurahan
            ON kelurahan.id_kel = laporan.id_kel
        JOIN kecamatan
            ON kecamatan.id_kec = kelurahan.id_kec
        JOIN kota_kabupaten
            ON kota_kabupaten.id_kota_kab = kecamatan.id_kota_kab
        JOIN provinsi
            ON provinsi.id_provinsi = kota_kabupaten.id_provinsi
        WHERE laporan.id_status = 'ST2'
          AND laporan.status_hapus = 0
        ORDER BY laporan.tanggal_kejadian DESC,
                 laporan.jam_kejadian DESC
        """, nativeQuery = true)
    List<RekapLaporanTerverifikasi> daftarLaporanTerverifikasi();
}