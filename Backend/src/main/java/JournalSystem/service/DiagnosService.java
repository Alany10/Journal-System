package JournalSystem.service;

import JournalSystem.model.Diagnos;
import JournalSystem.model.Patient;
import JournalSystem.model.Practitioner;
import JournalSystem.repository.IDiagnosRepository;
import JournalSystem.service.interfaces.IDiagnosService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DiagnosService implements IDiagnosService {

    private final IDiagnosRepository diagnosRepository;

    public DiagnosService(IDiagnosRepository diagnosRepository) {
        this.diagnosRepository = diagnosRepository;
    }

    @Override
    public List<Diagnos> getAllDiagnoses() {
        return diagnosRepository.findAll();
    }

    @Override
    public Diagnos getDiagnosById(int id) {
        Diagnos diagnos = diagnosRepository.findById(id).orElse(null);

        if (diagnos == null) throw new IllegalArgumentException("Diagnos with id " + id + " does not exist");

        return diagnos;
    }

    @Override
    public Diagnos createDiagnos(Diagnos diagnos) {
        return diagnosRepository.save(diagnos);
    }

    @Override
    public Diagnos updateDiagnos(int id, Diagnos diagnos) {
        if (!diagnosRepository.existsById(id)) throw new IllegalArgumentException("Diagnos with id " + id + " does not exist");

        diagnos.setId(id);
        return diagnosRepository.save(diagnos);
    }

    @Override
    public void deleteDiagnos(int id) {
        Diagnos diagnos = diagnosRepository.findById(id).orElse(null);

        if (diagnos == null) throw new IllegalArgumentException("Diagnos with id " + id + " does not exist");

        diagnosRepository.deleteById(id);
    }

    @Override
    public List<Diagnos> getAllDiagnosesByPatient(Patient patient) {
        if (patient == null) throw new IllegalStateException("Patient is null");

        return diagnosRepository.findByPatient_Id(patient.getId());
    }

    @Override
    public List<Diagnos> getAllDiagnosesByPractitioner(Practitioner practitioner) {
        if (practitioner == null) throw new IllegalStateException("Practitioner is null");

        return diagnosRepository.findByPractitioner_Id(practitioner.getId());
    }

    @Override
    public boolean existsById(int id) {
        return diagnosRepository.existsById(id);
    }
}
