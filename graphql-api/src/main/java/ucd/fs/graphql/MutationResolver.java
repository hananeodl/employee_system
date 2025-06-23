package ucd.fs.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import ucd.fs.client.PerformanceReviewClient;
import ucd.fs.model.*;
import java.time.LocalDate;
import java.util.Date;

@Component
public class MutationResolver implements GraphQLMutationResolver {

    private final PerformanceReviewClient performanceReviewClient;

    public MutationResolver(PerformanceReviewClient performanceReviewClient) {
        this.performanceReviewClient = performanceReviewClient;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(String username, String email, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Payroll createPayroll(Long employeeId, Double baseSalary, Double bonus, Double deductions, Date paymentDate) {
        Payroll payroll = new Payroll();
        payroll.setEmployeeId(employeeId);
        payroll.setBaseSalary(baseSalary);
        payroll.setBonus(bonus);
        payroll.setDeductions(deductions);
        payroll.setNetSalary(baseSalary + bonus - deductions);
        payroll.setPaymentDate(paymentDate);
        return payroll;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Absence createAbsence(Long employeeId, String startDate, String endDate, String reason, String status) {
        Absence absence = new Absence();
        absence.setEmployeeId(employeeId);
        absence.setStartDate(startDate);
        absence.setEndDate(endDate);
        absence.setReason(reason);
        absence.setStatus(status);
        return absence;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PerformanceReview createPerformanceReview(Long employeeId, int score, String comments, String reviewDate) {
        PerformanceReview performanceReview = new PerformanceReview();
        performanceReview.setEmployeeId(employeeId);
        performanceReview.setScore(score);
        performanceReview.setComments(comments);
        performanceReview.setReviewDate(LocalDate.parse(reviewDate));
        return performanceReview;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deletePerformanceReview(Long id) {
        performanceReviewClient.deleteReview(id);
        return true;
    }
}