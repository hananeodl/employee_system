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

    @Override
    public Payroll updatePayroll(Long id, Payroll updatedPayroll) {
        Payroll existing = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        existing.setBaseSalary(updatedPayroll.getBaseSalary());
        existing.setBonus(updatedPayroll.getBonus());
        existing.setDeductions(updatedPayroll.getDeductions());
        existing.setNetSalary(updatedPayroll.getNetSalary());

        return payrollRepository.save(existing);
    }

    @Override
    public List<Payroll> getPayrollsByEmployeeId(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<Payroll> findAll() {
        return payrollRepository.findAll();
    }

    @Override
    public void deleteByEmployeeId(Long employeeId) {
        payrollRepository.deleteById(employeeId);
    }
}

