package ucd.fs.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PerformanceReview {
    private Long id;
    private Long employeeId;
    private int score;
    private String comments;
    private LocalDate reviewDate;
}
