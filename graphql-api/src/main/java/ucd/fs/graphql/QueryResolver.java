package ucd.fs.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import ucd.fs.client.AbsenceManagementClient;
import ucd.fs.client.EmployeeProfileClient;
import ucd.fs.client.PayrollClient;
import ucd.fs.client.PerformanceReviewClient;
import ucd.fs.model.Absence;
import ucd.fs.model.Payroll;
import ucd.fs.model.PerformanceReview;
import ucd.fs.model.User;
import java.util.List;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    private final EmployeeProfileClient employeeProfileClient;
    private final PayrollClient payrollClient;
    private final AbsenceManagementClient absenceManagementClient;
    private final PerformanceReviewClient performanceReviewClient;

    public QueryResolver(EmployeeProfileClient employeeProfileClient,
                         PayrollClient payrollClient,
                         AbsenceManagementClient absenceManagementClient,
                         PerformanceReviewClient performanceReviewClient) {
        this.employeeProfileClient = employeeProfileClient;
        this.payrollClient = payrollClient;
        this.absenceManagementClient = absenceManagementClient;
        this.performanceReviewClient = performanceReviewClient;
    }

    public User getUser(Long id) {
        return employeeProfileClient.getUserById(id);
    }

    public List<Payroll> getPayrolls(Long employeeId) {
        return payrollClient.getPayrollsByEmployeeId(employeeId);
    }

    public List<Absence> getAbsences(Long employeeId) {
        return absenceManagementClient.getAbsencesByEmployeeId(employeeId);
    }

    public List<PerformanceReview> getPerformanceReviews(Long employeeId) {
        return performanceReviewClient.getReviewsByEmployeeId(employeeId);
    }
}

