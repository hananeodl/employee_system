package com.example.employeeprofile;

import com.example.employeeprofile.model.Role;
import com.example.employeeprofile.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeProfileApplication.class, args);
    }
    @Bean
    public CommandLineRunner run(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_USER"));
            }
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_ADMIN"));
            }
        };
    }
}
