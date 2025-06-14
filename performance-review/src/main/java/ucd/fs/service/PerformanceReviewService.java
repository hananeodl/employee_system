package ucd.fs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucd.fs.model.PerformanceReview;
import ucd.fs.repository.PerformanceReviewRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PerformanceReviewService {

    @Autowired
    private PerformanceReviewRepository repository;

    public PerformanceReview saveReview(PerformanceReview review) {
        if (review.getEmployeeId() == null || review.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
        if (review.getScore() < 0 || review.getScore() > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        return repository.save(review);
    }

    public List<PerformanceReview> getAllReviews() {
        return repository.findAll();
    }

    public Optional<PerformanceReview> getReviewById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid review ID");
        }
        return repository.findById(id);
    }

    public List<PerformanceReview> getReviewsByEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
        return repository.findByEmployeeId(employeeId);
    }

    public void deleteReview(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid review ID");
        }
        repository.deleteById(id);
    }
}