package com.example.demo.repo;

import java.time.LocalDate;
import java.time.LocalTime;




public interface RekapLaporanBencana {

    String getId();

    String getBencana();

    String getStatus();

    String getLokasi();

    LocalDate getTanggal();

    LocalTime getJam();

    String getCatatan();

    String getGambar();
}

