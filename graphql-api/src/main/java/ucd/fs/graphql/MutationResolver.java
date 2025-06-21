package ucd.fs.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import ucd.fs.model.*;

import java.time.LocalDate;
import java.util.Date;

@Component
public class MutationResolver implements GraphQLMutationResolver {

    public User createUser(String username, String email, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    public Payroll createPayroll(Long employeeId, Double baseSalary, Double bonus, Double deductions,  Date paymentDate) {
        Payroll payroll = new Payroll();
        payroll.setEmployeeId(employeeId);
        payroll.setBaseSalary(baseSalary);
        payroll.setBonus(bonus);
        payroll.setDeductions(deductions);
        payroll.setNetSalary(baseSalary + bonus - deductions);
        payroll.setPaymentDate(paymentDate);
        return payroll;
    }

    public Absence createAbsence(Long employeeId, String startDate, String endDate, String reason, String status) {
        Absence absence = new Absence();
        absence.setEmployeeId(employeeId);
        absence.setStartDate(startDate);
        absence.setEndDate(endDate);
        absence.setReason(reason);
        absence.setStatus(status);
        return absence;
    }

    public PerformanceReview createPerformanceReview(Long employeeId, int score, String comments, LocalDate reviewDate) {
        PerformanceReview performanceReview = new PerformanceReview();
        performanceReview.setEmployeeId(employeeId);
        performanceReview.setScore(score);
        performanceReview.setComments(comments);
        performanceReview.setReviewDate(reviewDate);
        return performanceReview;
    }
}