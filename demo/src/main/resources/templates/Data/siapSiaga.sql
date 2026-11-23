--Provinsi
-- INSERT INTO("JBR","Jawa Barat")
-- INSERT INTO("JTG","Jawa Tengah")
-- INSERT INTO("JKT","Jakarta")
-- INSERT INTO("BLI","Bali")

--Kota/Kabupaten 
-- INSERT INTO("BDG","Bandung")
-- INSERT INTO("SMG","Semarang")
-- INSERT INTO("JKT","Jakarta")
-- INSERT INTO("BLI","Bali")

--Kelurahan

--Kecamatan

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
-- Id_sekolah (PK) | nama_sekolah | alamat | id_kel (FK)
-- INSERT INTO("S1","SD Melati 1","Jln.Merdeka","FK KELURAHAN")
-- INSERT INTO("S2","SD Mawar","Jln.Aceh","FK KELURAHAN")
-- INSERT INTO("S3","SMP Mentari","Jln.Anggrek","FK KELURAHAN")
-- INSERT INTO("S4","SMP Harapan","Jln.Garuda","FK KELURAHAN")
-- INSERT INTO("S5","SMA Cahaya","Jln.Elang","FK KELURAHAN")
-- INSERT INTO("S6","SMA Ceria","Jln.Bima","FK KELURAHAN")


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

--Laporan_Bencana Ada
INSERT INTO Laporan_Bencana VALUES
('L01', 'FK KELURAHAN', NULL, 'ST2', 'B01', 1234061006000001, 'Banjir akibat hujan deras menyebabkan air masuk ke rumah warga.', 'banjir1.jpg', '2026-11-01', '14:00:00', FALSE),
('L02', 'FK KELURAHAN', NULL, 'ST2', 'B02', 2341061006000001, 'Longsor menutupi sebagian akses jalan menuju permukiman warga.', 'longsor1.jpg', '2026-11-02', '09:30:00', FALSE),
('L03', 'FK KELURAHAN', NULL, 'ST4', 'B03', 3412061006000001, 'Terjadi kebakaran hutan di sekitar area perbukitan.', 'kebakaran1.jpg', '2026-11-03', '11:15:00', FALSE),
('L04', 'FK KELURAHAN', NULL, 'ST2', 'B04', 4321061006000001, 'Gempa bumi dirasakan oleh warga di sekitar permukiman.', 'gempa1.jpg', '2026-11-04', '07:45:00', FALSE),
('L05', 'FK KELURAHAN', NULL, 'ST2', 'B05', 2143061006000001, 'Abu vulkanik mulai turun di sekitar permukiman warga.', 'gunung1.jpg', '2026-11-05', '06:30:00', FALSE),
('L06', 'FK KELURAHAN', 'S1', 'ST2', 'B06', 3124061006000001, 'Sejumlah siswa mengalami gejala keracunan setelah mengonsumsi makanan dari program MBG.', 'mbg1.jpg', '2026-11-06', '12:30:00', FALSE);

--Update_Bencana Ada
INSERT INTO Update_Bencana VALUES
('U001', 'L01', 1234061006000001, 'ST1', 'Air mulai memasuki rumah warga dengan ketinggian sekitar 30 cm.', 'update1.jpg', '2026-11-01', '15:00:00'),
('U002', 'L01', 1234061006000001, 'ST1', 'Ketinggian air semakin meningkat dan beberapa warga mulai mengungsi.', 'update2.jpg', '2026-11-01', '16:30:00'),
('U003', 'L02', 2341061006000001, 'ST1', 'Material longsor masih menutupi sebagian akses jalan.', 'update3.jpg', '2026-11-02', '11:00:00'),
('U004', 'L04', 4321061006000001, 'ST1', 'Kondisi warga mulai membaik setelah gempa berhenti.', 'update4.jpg', '2026-11-04', '08:30:00'),
('U005', 'L05', 2143061006000001, 'ST1', 'Abu vulkanik mulai menutupi beberapa rumah warga.', 'update5.jpg', '2026-11-05', '08:00:00'),
('U006', 'L06', 3124061006000001, 'ST1', 'Beberapa siswa telah dibawa ke fasilitas kesehatan terdekat.', 'update6.jpg', '2026-11-06', '13:30:00');


-- --Log_Penanganan
-- INSERT INTO("LG1","L02",3273010101900001,8,"Melakukan pemeriksaan dan pengamanan lokasi longsor.","2026-11-02","penanganan1.jpg")
-- INSERT INTO("LG2","L02",3273010202900002,12,"Membersihkan material longsor yang menutupi akses jalan.","2026-11-03","penanganan2.jpg")
-- INSERT INTO("LG3","L03",3273010303900003,15,"Melakukan pemadaman api di area hutan terdampak.","2026-11-03","penanganan3.jpg")
-- INSERT INTO("LG4","L04",3273010404900004,6,"Melakukan pendataan warga dan pemeriksaan kondisi bangunan.","2026-11-04","penanganan4.jpg")
-- INSERT INTO("LG5","L05",3273010505900005,10,"Melakukan pemantauan dampak abu vulkanik dan membagikan masker kepada warga.","2026-11-05","penanganan5.jpg")
-- INSERT INTO("LG6","L06",3273010606900006,8,"Melakukan pendataan siswa terdampak dan membantu proses evakuasi menuju fasilitas kesehatan.","2026-11-06","penanganan6.jpg")

-- --Pengumuman 
-- INSERT INTO("P01",3273010101900001,"L02","Masyarakat di sekitar lokasi longsor diimbau menghindari area terdampak karena proses pembersihan masih berlangsung.","Sekitar lokasi longsor")
-- INSERT INTO("P02",3273010303900003,"L03","Warga diminta menjauhi area kebakaran hutan dan tidak melakukan aktivitas di sekitar lokasi terdampak.","Area perbukitan")
-- INSERT INTO("P03",3273010404900004,"L04","Masyarakat diimbau tetap waspada terhadap kemungkinan gempa susulan dan memeriksa kondisi bangunan.","Wilayah terdampak gempa")
-- INSERT INTO("P04",3273010505900005,"L05","Warga di sekitar gunung diimbau menggunakan masker untuk mengurangi paparan abu vulkanik.","Area sekitar gunung")
-- INSERT INTO("P05",3273010606900006,"L06","Orang tua dan masyarakat diimbau tetap waspada serta mengikuti informasi resmi terkait penanganan siswa yang mengalami gejala keracunan MBG.","Sekolah terdampak")
-- INSERT INTO("P06",3273010202900002,"L01","Warga diimbau berhati-hati dan menghindari area yang masih tergenang banjir.","Area permukiman terdampak")