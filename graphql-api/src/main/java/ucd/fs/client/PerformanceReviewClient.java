package ucd.fs.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ucd.fs.model.PerformanceReview;
import java.util.List;

@FeignClient(name = "performance-review", url = "${services.performance-review-url}")
public interface PerformanceReviewClient {
    @GetMapping("/reviews")
    List<PerformanceReview> getReviewsByEmployeeId(@RequestParam("employeeId") Long employeeId);
}
