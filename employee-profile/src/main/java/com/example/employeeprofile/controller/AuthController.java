package com.example.employeeprofile.controller;

import com.example.employeeprofile.model.User;
import com.example.employeeprofile.security.SecurityConstants;
import com.example.employeeprofile.service.PasswordResetService;
import com.example.employeeprofile.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/app")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordResetService passwordResetService;
    public AuthController(UserService userService, AuthenticationManager authenticationManager, PasswordResetService passwordResetService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordResetService = passwordResetService;

    }

    //  Endpoint Sign-Up
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String email = request.get("email");
            String password = request.get("password");
            String role = request.getOrDefault("role", "ROLE_USER");

            User user = userService.registerUser(username, email, password, role);

            log.info(" Utilisateur enregistré : {}", username);

            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully",
                    "username", user.getUsername()
            ));
        } catch (Exception e) {
            log.warn(" Échec d'enregistrement : {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //  Endpoint Sign-In avec journalisation
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info(" Connexion réussie pour l'utilisateur : {}", authentication.getName());

            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("USER");

            String accessToken = Jwts.builder()
                    .setIssuer("EmployeeProfileApp")
                    .setSubject(authentication.getName())
                    .claim("role", role)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 min
                    .signWith(key)
                    .compact();

            String refreshToken = Jwts.builder()
                    .setIssuer("EmployeeProfileApp")
                    .setSubject(authentication.getName())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 jours
                    .signWith(key)
                    .compact();

            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            ));

        } catch (Exception e) {
            log.warn(" Échec de connexion pour l'utilisateur : {}", request.getUsername());
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }
    }

    //  Endpoint GET /me (utilisateur connecté)
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn(" Accès non autorisé à /me");
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username);

        log.info(" Données récupérées pour l'utilisateur : {}", username);

        return ResponseEntity.ok(Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole().getName()
        ));
    }

    //  Endpoint de refresh token
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || refreshToken.isEmpty()) {
            log.warn(" Refresh token manquant");
            return ResponseEntity.status(401).body(Map.of("error", "Refresh token is missing or invalid"));
        }

        try {
            if (refreshToken.startsWith("Bearer ")) {
                refreshToken = refreshToken.substring(7);
            }

            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            String username = claims.getSubject();

            String newAccessToken = Jwts.builder()
                    .setIssuer("EmployeeProfileApp")
                    .setSubject(username)
                    .claim("role", claims.get("role"))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 min
                    .signWith(key)
                    .compact();

            log.info(" Nouveau access token généré pour : {}", username);

            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));

        } catch (Exception e) {
            log.warn(" Refresh token invalide");
            return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
        }
    }
    //  Endpoint pour demander un token de réinitialisation
    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        try {
            String token = passwordResetService.createResetToken(username);
            // Pour l'exemple, on retourne directement le token.
            // En prod, on l’enverrait par email.
            return ResponseEntity.ok(Map.of("resetToken", token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //  Endpoint pour réinitialiser le mot de passe avec le token
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");

        boolean success = passwordResetService.resetPassword(token, newPassword);
        if (success) {
            return ResponseEntity.ok(Map.of("message", "Mot de passe réinitialisé avec succès."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Token invalide ou expiré."));
        }
    }
}
