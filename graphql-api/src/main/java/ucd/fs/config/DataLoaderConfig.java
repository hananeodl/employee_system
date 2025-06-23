package ucd.fs.config;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucd.fs.client.*;
import ucd.fs.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
                            Long employeeId = (Long) employeeIds.get(0);
                            User user = employeeClient.getUserById(employeeId);
                            return List.of(user);
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