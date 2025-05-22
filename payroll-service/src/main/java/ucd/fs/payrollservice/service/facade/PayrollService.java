package ucd.fs.payrollservice.service.facade;

import ucd.fs.payrollservice.bean.Payroll;

import java.util.List;

public interface PayrollService {
    Payroll savePayroll(Payroll payroll);
    List<Payroll> getPayrollByEmployee(Long employeeId);
}

