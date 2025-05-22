package ucd.fs.payrollservice.ws.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucd.fs.payrollservice.bean.Payroll;
import ucd.fs.payrollservice.service.facade.PayrollService;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @PostMapping
    public ResponseEntity<Payroll> savePayroll(@RequestBody Payroll payroll) {
        return ResponseEntity.ok(payrollService.savePayroll(payroll));
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<Payroll>> getPayrollByEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(payrollService.getPayrollByEmployee(id));
    }
}

