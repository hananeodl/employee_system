package ucd.fs.absencemanagement.service.facade;

import ucd.fs.absencemanagement.bean.Absence;

import java.util.List;

public interface AbsenceService {
    Absence createAbsence(Absence absence);
    List<Absence> getAllAbsences();
    Absence approveAbsence(Long id);
    List<Absence> getAbsencesByEmployeeId(Long employeeId);
}