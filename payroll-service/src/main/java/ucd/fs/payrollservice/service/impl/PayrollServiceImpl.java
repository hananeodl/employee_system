package ucd.fs.payrollservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucd.fs.payrollservice.bean.Payroll;
import ucd.fs.payrollservice.dao.PayrollDao;
import ucd.fs.payrollservice.service.facade.PayrollService;

import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollDao payrollRepository;

    @Override
    public Payroll savePayroll(Payroll payroll) {
        double net = payroll.getBaseSalary() + payroll.getBonus() - payroll.getDeductions();
        payroll.setNetSalary(net);
        return payrollRepository.save(payroll);
    }

    @Override
    public List<Payroll> getPayrollByEmployee(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }
}

