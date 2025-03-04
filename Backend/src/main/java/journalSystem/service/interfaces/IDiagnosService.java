package journalSystem.service.interfaces;

import journalSystem.model.Diagnos;

import java.util.List;

public interface IDiagnosService {
    List<Diagnos> getAllDiagnoses();
    Diagnos getDiagnosById(int id);
    Diagnos createDiagnos(Diagnos diagnos);
    Diagnos updateDiagnos(int id, Diagnos diagnos);
    Diagnos establishDiagnos(int id, Diagnos diagnos);
    void deleteDiagnos(int id);
    List<Diagnos> getAllDiagnosesByPatient(int patientId);
    List<Diagnos> getAllDiagnosesByPractitioner(int practitionerId);
    boolean existsById(int id);
}
