package ucd.fs.model;

import lombok.Data;
import java.util.List;

@Data
public class EmployeeDetails {
    private User profile;
    private List<Payroll> payrolls;
    private List<Absence> absences;
    private List<PerformanceReview> performanceReviews;
}