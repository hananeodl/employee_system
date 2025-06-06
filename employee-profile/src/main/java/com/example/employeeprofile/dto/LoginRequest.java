package com.example.employeeprofile.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

    // Getters et Setters
}