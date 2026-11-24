-- Buat database kalau belum ada (tabel dibuat otomatis oleh Spring saat bootRun)
IF DB_ID('SiapSiaga') IS NULL CREATE DATABASE SiapSiaga;
GO

USE SiapSiaga;
GO

-- Kosongkan data lama (urutan anak -> induk karena ada foreign key),
-- supaya file ini bisa dijalankan berulang kali tanpa error duplikat.
-- Syarat: tabel sudah ada (jalankan gradlew bootRun sekali dulu).
DELETE FROM pengumuman;
DELETE FROM log_penanganan;
DELETE FROM update_bencana;
DELETE FROM laporan_bencana;
DELETE FROM sekolah;
DELETE FROM kelurahan;
DELETE FROM kecamatan;
DELETE FROM kota_kabupaten;
DELETE FROM provinsi;
DELETE FROM petugas;
DELETE FROM warga;
DELETE FROM admin;
DELETE FROM bencana;
DELETE FROM status_laporan;
GO

--Provinsi
INSERT INTO provinsi (id_provinsi, nama_provinsi) VALUES
('JBR', 'Jawa Barat'),
('JTG', 'Jawa Tengah'),
('JKT', 'Jakarta'),
('BLI', 'Bali');

--Kota/Kabupaten
INSERT INTO kota_kabupaten (id_kota_kab, id_provinsi, nama_kota_kab) VALUES
('BDG', 'JBR', 'Bandung'),
('SMG', 'JTG', 'Semarang'),
('JKT', 'JKT', 'Jakarta'),
('BLI', 'BLI', 'Bali');

--Kecamatan
INSERT INTO kecamatan (id_kec, nama_kec, id_kota_kab) VALUES
('KC1', 'Coblong', 'BDG'),
('KC2', 'Tembalang', 'SMG'),
('KC3', 'Menteng', 'JKT'),
('KC4', 'Kuta', 'BLI');

--Kelurahan
INSERT INTO kelurahan (id_kel, nama_kel, id_kec) VALUES
('KL1', 'Dago', 'KC1'),
('KL2', 'Sendangmulyo', 'KC2'),
('KL3', 'Cikini', 'KC3'),
('KL4', 'Legian', 'KC4');


--Petugas Ada
INSERT INTO Petugas VALUES
(1234061006000002, 'Andi Pratama', 'andi1'),
(4321061006000002, 'Siti Rahmawati', 'siti1'),
(3421061006000002, 'Bagas Ramadhan', 'bagas1'),
(4213061006000002, 'Rizky Maulana', 'rizky1'),
(4312061006000002, 'Dinda Permatasari', 'dinda1'),
(1432061006000002, 'Fajar Nugroho', 'fajar1');


--Warga Ada
INSERT INTO Warga VALUES
(1234061006000001, 'Keisya', 'keisya1'),
(2341061006000001, 'Nona', 'nona1'),
(3412061006000001, 'Felice', 'felice1'),
(4321061006000001, 'Clarisa', 'clarisa1'),
(2143061006000001, 'Natasya', 'natasya1'),
(3124061006000001, 'Helena', 'helena1');

--Admin Ada
INSERT INTO Admin VALUES
(1234030101900001, 'Admin', 'admin1');

--Sekolah Ada
INSERT INTO sekolah (id_sekolah, nama_sekolah, alamat, id_kel) VALUES
('S1', 'SD Melati 1', 'Jln.Merdeka', 'KL2'),
('S2', 'SD Mawar', 'Jln.Aceh', 'KL1'),
('S3', 'SMP Mentari', 'Jln.Anggrek', 'KL3'),
('S4', 'SMP Harapan', 'Jln.Garuda', 'KL4'),
('S5', 'SMA Cahaya', 'Jln.Elang', 'KL1'),
('S6', 'SMA Ceria', 'Jln.Bima', 'KL2');


--Status_Laporan Ada
INSERT INTO Status_Laporan VALUES
('ST1', 'Menunggu Verifikasi'),
('ST2', 'Terverifikasi / On Progress'),
('ST3', 'Tidak Terverifikasi'),
('ST4', 'Selesai');

--Bencana Ada
INSERT INTO Bencana VALUES
('B01', 'Banjir'),
('B02', 'Tanah Longsor'),
('B03', 'Kebakaran Hutan'),
('B04', 'Gempa Bumi'),
('B05', 'Gunung Meletus'),
('B06', 'Keracunan MBG');

--Laporan_Bencana Ada (foto diisi NULL karena kolom foto bertipe VARBINARY)
INSERT INTO laporan_bencana (id_laporan, id_kel, id_sekolah, id_status, id_bencana, id_warga, catatan, foto, tanggal_kejadian, jam_kejadian, status_hapus) VALUES
('L01', 'KL3', NULL, 'ST2', 'B01', '1234061006000001', 'Banjir akibat hujan deras menyebabkan air masuk ke rumah warga.', NULL, '2026-11-01', '14:00:00', 0),
('L02', 'KL1', NULL, 'ST2', 'B02', '2341061006000001', 'Longsor menutupi sebagian akses jalan menuju permukiman warga.', NULL, '2026-11-02', '09:30:00', 0),
('L03', 'KL4', NULL, 'ST4', 'B03', '3412061006000001', 'Terjadi kebakaran hutan di sekitar area perbukitan.', NULL, '2026-11-03', '11:15:00', 0),
('L04', 'KL2', NULL, 'ST2', 'B04', '4321061006000001', 'Gempa bumi dirasakan oleh warga di sekitar permukiman.', NULL, '2026-11-04', '07:45:00', 0),
('L05', 'KL1', NULL, 'ST2', 'B05', '2143061006000001', 'Abu vulkanik mulai turun di sekitar permukiman warga.', NULL, '2026-11-05', '06:30:00', 0),
('L06', 'KL2', 'S1', 'ST2', 'B06', '3124061006000001', 'Sejumlah siswa mengalami gejala keracunan setelah mengonsumsi makanan dari program MBG.', NULL, '2026-11-06', '12:30:00', 0);

--Update_Bencana Ada
INSERT INTO update_bencana (id_update, id_laporan, nik, id_status, detail_update, foto_terbaru, tanggal_update, jam_update) VALUES
('U001', 'L01', '1234061006000001', 'ST1', 'Air mulai memasuki rumah warga dengan ketinggian sekitar 30 cm.', NULL, '2026-11-01', '15:00:00'),
('U002', 'L01', '1234061006000001', 'ST1', 'Ketinggian air semakin meningkat dan beberapa warga mulai mengungsi.', NULL, '2026-11-01', '16:30:00'),
('U003', 'L02', '2341061006000001', 'ST1', 'Material longsor masih menutupi sebagian akses jalan.', NULL, '2026-11-02', '11:00:00'),
('U004', 'L04', '4321061006000001', 'ST1', 'Kondisi warga mulai membaik setelah gempa berhenti.', NULL, '2026-11-04', '08:30:00'),
('U005', 'L05', '2143061006000001', 'ST1', 'Abu vulkanik mulai menutupi beberapa rumah warga.', NULL, '2026-11-05', '08:00:00'),
('U006', 'L06', '3124061006000001', 'ST1', 'Beberapa siswa telah dibawa ke fasilitas kesehatan terdekat.', NULL, '2026-11-06', '13:30:00');

-- -- --Log_Penanganan
-- INSERT INTO log_penanganan (id_log, id_laporan, nik, jumlah_personel, aktivitas, tanggal_penanganan, foto_penanganan) VALUES
-- ('LG1', 'L02', '1234061006000001', 8, 'Melakukan pemeriksaan dan pengamanan lokasi longsor.', '2026-11-02', NULL),
-- ('LG2', 'L02', '2341061006000001', 12, 'Membersihkan material longsor yang menutupi akses jalan.', '2026-11-03', NULL),
-- ('LG3', 'L03', '3412061006000001', 15, 'Melakukan pemadaman api di area hutan terdampak.', '2026-11-03', NULL),
-- ('LG4', 'L04', '4321061006000001', 6, 'Melakukan pendataan warga dan pemeriksaan kondisi bangunan.', '2026-11-04', NULL),
-- ('LG5', 'L05', '2143061006000001', 10, 'Melakukan pemantauan dampak abu vulkanik dan membagikan masker kepada warga.', '2026-11-05', NULL),
-- ('LG6', 'L06', '3124061006000001', 8, 'Melakukan pendataan siswa terdampak dan membantu proses evakuasi menuju fasilitas kesehatan.', '2026-11-06', NULL);

-- -- --Pengumuman 

-- INSERT INTO pengumuman (id_pengumuman, id_laporan, nik, isi_pengumuman, detail_lokasi) VALUES

-- ('P01','L02','1234061006000001','Masyarakat di sekitar lokasi longsor diimbau menghindari area terdampak karena proses pembersihan masih berlangsung.','Sekitar lokasi longsor'),
-- ('P02','L03','3412061006000001','Warga diminta menjauhi area kebakaran hutan dan tidak melakukan aktivitas di sekitar lokasi terdampak.','Area perbukitan'),
-- ('P03','L04','4321061006000001','Masyarakat diimbau tetap waspada terhadap kemungkinan gempa susulan dan memeriksa kondisi bangunan.','Wilayah terdampak gempa'),
-- ('P04','L05','2143061006000001','Warga di sekitar gunung diimbau menggunakan masker untuk mengurangi paparan abu vulkanik.','Area sekitar gunung'),
-- ('P05','L06','3124061006000001','Orang tua diimbau tetap waspada dan menunggu informasi resmi terkait keracunan MBG.','Sekolah terdampak'),
-- ('P06','L01','2341061006000001','Warga diimbau berhati-hati dan menghindari area yang masih tergenang banjir.','Area permukiman terdampak');


-- --Log_Penanganan
INSERT INTO log_penanganan (id_log, id_laporan, nik, jumlah_personel, aktivitas, tanggal_penanganan, foto_penanganan) VALUES
('LG1', 'L02', 1234061006000002, 8, 'Melakukan pemeriksaan dan pengamanan lokasi longsor.', '2026-11-02', NULL),
('LG2', 'L02', 4321061006000002, 12, 'Membersihkan material longsor yang menutupi akses jalan.', '2026-11-03', NULL),
('LG3', 'L03', 3421061006000002, 15, 'Melakukan pemadaman api di area hutan terdampak.', '2026-11-03', NULL),
('LG4', 'L04', 4213061006000002, 6, 'Melakukan pendataan warga dan pemeriksaan kondisi bangunan.', '2026-11-04', NULL),
('LG5', 'L05', 4312061006000002, 10, 'Melakukan pemantauan dampak abu vulkanik dan membagikan masker kepada warga.', '2026-11-05', NULL),
('LG6', 'L06', 1432061006000002, 8, 'Melakukan pendataan siswa terdampak dan membantu proses evakuasi menuju fasilitas kesehatan.', '2026-11-06', NULL);

-- --Pengumuman 
INSERT INTO pengumuman (id_pengumuman, id_laporan, nik, isi_pengumuman, detail_lokasi) VALUES
('P01', 'L02', 1234061006000002, 'Masyarakat di sekitar lokasi longsor diimbau menghindari area terdampak karena proses pembersihan masih berlangsung.', 'Sekitar lokasi longsor'),
('P02', 'L03', 3421061006000002, 'Warga diminta menjauhi area kebakaran hutan dan tidak melakukan aktivitas di sekitar lokasi terdampak.', 'Area perbukitan'),
('P03', 'L04', 4213061006000002, 'Masyarakat diimbau tetap waspada terhadap kemungkinan gempa susulan dan memeriksa kondisi bangunan.', 'Wilayah terdampak gempa'),
('P04', 'L05', 4312061006000002, 'Warga di sekitar gunung diimbau menggunakan masker untuk mengurangi paparan abu vulkanik.', 'Area sekitar gunung'),
('P05', 'L06', 1432061006000002, 'Orang tua diimbau tetap waspada dan menunggu informasi resmi terkait keracunan MBG.', 'Sekolah terdampak'),
('P06', 'L01', 4321061006000002, 'Warga diimbau berhati-hati dan menghindari area yang masih tergenang banjir.', 'Area permukiman terdampak');
