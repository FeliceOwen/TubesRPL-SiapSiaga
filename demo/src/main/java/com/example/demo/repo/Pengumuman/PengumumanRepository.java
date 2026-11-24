package com.example.demo.repo.Pengumuman;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pengumuman;
@Repository 
public interface PengumumanRepository extends JpaRepository<Pengumuman, String> {
    
@Query (value = """
        SELECT 
            bencana.nama_bencana AS jenis,
            CONCAT_WS(', ', kelurahan.nama_kel, kecamatan.nama_kec, kota_kabupaten.nama_kota_kab, provinsi.nama_provinsi) AS lokasi,
            CONVERT(VARCHAR(10), laporan.tanggal_kejadian, 23) AS tanggal,
            CONVERT(VARCHAR(5), laporan.jam_kejadian, 108) AS jam,
            laporan.catatan AS catatan,
            laporan.foto AS gambar
        FROM
            pengumuman
        JOIN 
            laporan_bencana AS laporan ON pengumuman.id_laporan = laporan.id_laporan
        JOIN
            kelurahan ON kelurahan.id_kel = laporan.id_kel
        JOIN 
            kecamatan ON kecamatan.id_kec = kelurahan.id_kec
        JOIN 
            kota_kabupaten ON kota_kabupaten.id_kota_kab = kecamatan.id_kota_kab
        JOIN
            provinsi ON provinsi.id_provinsi = kota_kabupaten.id_provinsi         
        JOIN 
            bencana ON bencana.id_bencana = laporan.id_bencana   
        WHERE
            laporan.id_status = 'ST2'     
        """, nativeQuery=true)
        List<RekapPengumuman> pengumumanAktif();
}

