package ucd.fs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucd.fs.model.PerformanceReview;
import ucd.fs.service.PerformanceReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
public class PerformanceReviewController {

    @Autowired
    private PerformanceReviewService service;

    @PostMapping
    public ResponseEntity<PerformanceReview> createReview(@RequestBody PerformanceReview review) {
        return ResponseEntity.ok(service.saveReview(review));
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

