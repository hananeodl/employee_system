package ucd.fs.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ucd.fs.model.Payroll;
import java.util.List;

@FeignClient(name = "payroll", url = "${services.payroll-url}")
public interface PayrollClient {
    @GetMapping("/payrolls")
    List<Payroll> getPayrollsByEmployeeId(@RequestParam("employeeId") Long employeeId);
}
