package ucd.fs.model;

import lombok.Data;
import java.util.Date;

@Data
public class Payroll {
    private Long id;
    private Long employeeId;
    private Double baseSalary;
    private Double bonus;
    private Double deductions;
    private Double netSalary;
    private Date paymentDate;
}
