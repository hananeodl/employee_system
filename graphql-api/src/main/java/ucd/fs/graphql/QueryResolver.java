package ucd.fs.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import ucd.fs.client.*;
import ucd.fs.model.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {

    private final EmployeeProfileClient employeeProfileClient;
    private final PayrollClient payrollClient;
    private final AbsenceManagementClient absenceManagementClient;
    private final PerformanceReviewClient performanceReviewClient;

    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<User> getUser(Long id, DataFetchingEnvironment env) {
        DataLoader<Long, User> loader = env.getDataLoader("userLoader");
        return loader.load(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<List<Payroll>> getPayrolls(Long employeeId, DataFetchingEnvironment env) {
        DataLoader<Long, List<Payroll>> loader = env.getDataLoader("payrollLoader");
        return loader.load(employeeId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public CompletableFuture<List<Absence>> getAbsences(Long employeeId, DataFetchingEnvironment env) {
        DataLoader<Long, List<Absence>> loader = env.getDataLoader("absenceLoader");
        return loader.load(employeeId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public CompletableFuture<List<PerformanceReview>> getPerformanceReviews(Long employeeId, DataFetchingEnvironment env) {
        DataLoader<Long, List<PerformanceReview>> loader = env.getDataLoader("performanceLoader");
        return loader.load(employeeId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<EmployeeDetails> getEmployeeDetails(Long employeeId, DataFetchingEnvironment env) {
        DataLoader<Long, User> userLoader = env.getDataLoader("userLoader");
        DataLoader<Long, List<Payroll>> payrollLoader = env.getDataLoader("payrollLoader");
        DataLoader<Long, List<Absence>> absenceLoader = env.getDataLoader("absenceLoader");
        DataLoader<Long, List<PerformanceReview>> performanceLoader = env.getDataLoader("performanceLoader");

        return CompletableFuture.allOf(
                userLoader.load(employeeId),
                payrollLoader.load(employeeId),
                absenceLoader.load(employeeId),
                performanceLoader.load(employeeId)
        ).thenApply(v -> {
            EmployeeDetails details = new EmployeeDetails();
            details.setProfile(userLoader.load(employeeId).join());
            details.setPayrolls(payrollLoader.load(employeeId).join());
            details.setAbsences(absenceLoader.load(employeeId).join());
            details.setPerformanceReviews(performanceLoader.load(employeeId).join());
            return details;
        });
    }
}