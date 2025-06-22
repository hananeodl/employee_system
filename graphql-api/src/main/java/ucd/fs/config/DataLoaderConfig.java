package ucd.fs.config;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucd.fs.client.*;
import ucd.fs.model.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.Collections;

@Configuration
public class DataLoaderConfig {

    @Bean
    public DataLoaderRegistry dataLoaderRegistry(
            EmployeeProfileClient employeeClient,
            PayrollClient payrollClient,
            AbsenceManagementClient absenceClient,
            PerformanceReviewClient performanceClient) {

        DataLoaderRegistry registry = new DataLoaderRegistry();

        registry.register("userLoader",
                DataLoader.newDataLoader(employeeIds ->
                        CompletableFuture.supplyAsync(() -> {
                            Long employeeId = (Long) employeeIds.get(0); // Cast explicite en Long
                            return Collections.singletonList(employeeClient.getUserById(employeeId));
                        })));

        registry.register("payrollLoader",
                DataLoader.newDataLoader(employeeIds ->
                        CompletableFuture.supplyAsync(() -> {
                            Long employeeId = (Long) employeeIds.get(0);
                            return payrollClient.getPayrollsByEmployeeId(employeeId);
                        })));

        registry.register("absenceLoader",
                DataLoader.newDataLoader(employeeIds ->
                        CompletableFuture.supplyAsync(() -> {
                            Long employeeId = (Long) employeeIds.get(0);
                            return absenceClient.getAbsencesByEmployeeId(employeeId);
                        })));

        registry.register("performanceLoader",
                DataLoader.newDataLoader(employeeIds ->
                        CompletableFuture.supplyAsync(() -> {
                            Long employeeId = (Long) employeeIds.get(0);
                            return performanceClient.getReviewsByEmployeeId(employeeId);
                        })));

        return registry;
    }
}