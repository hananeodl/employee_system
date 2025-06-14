package ucd.fs.absencemanagement.ws.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucd.fs.absencemanagement.bean.Absence;
import ucd.fs.absencemanagement.service.facade.AbsenceService;

import java.util.List;

@RestController
@RequestMapping("/api/absences/")
public class AbsenceController {
    @Autowired
    private AbsenceService absenceService;

    @PostMapping("")
    public ResponseEntity<Absence> createAbsence(@RequestBody Absence absence) {
        return ResponseEntity.ok(absenceService.createAbsence(absence));
    }

    @GetMapping("")
    public List<Absence> getAllAbsences() {
        return absenceService.getAllAbsences();
    }

    @PutMapping("{id}/approve")
    public ResponseEntity<Absence> approveAbsence(@PathVariable Long id) {
        return ResponseEntity.ok(absenceService.approveAbsence(id));
    }

    @GetMapping("employee/{employeeId}")
    public ResponseEntity<List<Absence>> getAbsencesByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(absenceService.getAbsencesByEmployeeId(employeeId));
    }
}



