package ucd.fs.absencemanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucd.fs.absencemanagement.bean.Absence;
import ucd.fs.absencemanagement.dao.AbsenceRepository;
import ucd.fs.absencemanagement.service.facade.AbsenceService;

import java.util.List;

@Service
public class AbsenceServiceImpl implements AbsenceService {
    @Autowired
    private AbsenceRepository absenceRepository;

    public Absence createAbsence(Absence absence) {
        return absenceRepository.save(absence);
    }

    public List<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }

    public Absence approveAbsence(Long id) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found"));

        absence.setStatus("APPROVED");
        return absenceRepository.save(absence);
    }

    public List<Absence> getAbsencesByEmployeeId(Long employeeId) {
        return absenceRepository.findByEmployeeId(employeeId);
    }
}

