package ucd.fs.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ucd.fs.model.Absence;
import java.util.List;

@FeignClient(name = "absence-management", url = "${services.absence-management-url}")
public interface AbsenceManagementClient {
    @GetMapping("/absences")
    List<Absence> getAbsencesByEmployeeId(@RequestParam("employeeId") Long employeeId);
}
