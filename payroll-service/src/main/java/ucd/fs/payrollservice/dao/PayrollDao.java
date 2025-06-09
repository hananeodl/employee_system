package ucd.fs.payrollservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucd.fs.payrollservice.bean.Payroll;

import java.util.List;

@Repository
public interface PayrollDao extends JpaRepository<Payroll, Long> {
    List<Payroll> findByEmployeeId(Long employeeId);
}
