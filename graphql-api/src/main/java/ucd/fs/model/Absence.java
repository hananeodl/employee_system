package ucd.fs.model;

import lombok.Data;

@Data
public class Absence {
    private Long id;
    private Long employeeId;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;
}
