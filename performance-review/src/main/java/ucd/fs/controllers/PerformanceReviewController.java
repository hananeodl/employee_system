package ucd.fs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ucd.fs.model.PerformanceReview;
import ucd.fs.security.BruteForceProtectionService;
import ucd.fs.service.PerformanceReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
public class PerformanceReviewController {

    @Autowired
    private PerformanceReviewService service;

    @Autowired
    private BruteForceProtectionService bruteForceProtectionService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody PerformanceReview review, Authentication authentication) {
        String username = authentication.getName();

        if (bruteForceProtectionService.isBlocked(username)) {
            return ResponseEntity.status(429).body("Trop de tentatives. Réessayez plus tard.");
        }

        try {
            PerformanceReview savedReview = service.saveReview(review);
            bruteForceProtectionService.loginSucceeded(username);
            return ResponseEntity.ok(savedReview);
        } catch (Exception e) {
            bruteForceProtectionService.loginFailed(username);
            return ResponseEntity.badRequest().body("Erreur lors de la création de la review : " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PerformanceReview>> getAllReviews() {
        return ResponseEntity.ok(service.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceReview> getReviewById(@PathVariable Long id) {
        return service.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PerformanceReview>> getReviewsByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getReviewsByEmployeeId(employeeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        service.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
