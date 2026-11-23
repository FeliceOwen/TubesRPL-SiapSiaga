package com.example.demo.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nik;
    private String nama;
    private String password;
}
