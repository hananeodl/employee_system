package ucd.fs.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import ucd.fs.model.PerformanceReview;
import ucd.fs.service.PerformanceReviewService;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PerformanceReviewResolver {

    @Autowired
    private PerformanceReviewService service;

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PerformanceReview> getAllReviews(@ContextValue(name = "jwt") String jwt) {
        return service.getAllReviews();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PerformanceReview getReviewById(@Argument Long id) {
        return service.getReviewById(id).orElse(null);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PerformanceReview> getReviewsByEmployeeId(@Argument Long employeeId) {
        return service.getReviewsByEmployeeId(employeeId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PerformanceReview createReview(
            @Argument Long employeeId,
            @Argument int score,
            @Argument String comments,
            @Argument String reviewDate
    ) {
        PerformanceReview review = PerformanceReview.builder()
                .employeeId(employeeId)
                .score(score)
                .comments(comments)
                .reviewDate(LocalDate.parse(reviewDate))
                .build();
        return service.saveReview(review);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteReview(@Argument Long id) {
        service.deleteReview(id);
        return true;
    }
}