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
        return repository.save(review);
    }

    public List<PerformanceReview> getAllReviews() {
        return repository.findAll();
    }

    public Optional<PerformanceReview> getReviewById(Long id) {
        return repository.findById(id);
    }

    public List<PerformanceReview> getReviewsByEmployeeId(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public void deleteReview(Long id) {
        repository.deleteById(id);
    }
}
