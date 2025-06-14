package com.example.employeeprofile.security;

import java.io.IOException;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Slf4j
public class JwtGeneratorFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtGeneratorFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            SecretKey key = Keys.hmacShaKeyFor(com.example.employeeprofile.security.SecurityConstants.JWT_KEY.getBytes());

            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("USER");

            String jwt = Jwts.builder()
                    .setIssuer("EmployeeProfileApp")
                    .setSubject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("role", role)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 min
                    .signWith(key)
                    .compact();
            response.setHeader("Authorization", "Bearer " + jwt);
            response.setHeader(com.example.employeeprofile.security.SecurityConstants.JWT_HEADER, "Bearer " + jwt);
        }

        filterChain.doFilter(request, response);
        log.info("JwtGeneratorFilter called for: " + request.getServletPath());

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Ne pas filtrer la génération du token pour le endpoint sign-in
        return !request.getServletPath().equals("/app/sign-in");
    }
}
