package com.example.employeeprofile.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class RegisterRequest {
    private String username;

    private String email;
    private String password;
    private String role; // "ROLE_USER" ou "ROLE_ADMIN"
}
