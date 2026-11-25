package com.example.demo.repo;

import java.time.LocalDate;
import java.time.LocalTime;

public interface RekapLaporanTerverifikasi {

    String getId();

    String getJenisBencana();

    String getLokasi();

    LocalDate getTanggal();

    LocalTime getWaktu();

    String getCatatan();
}