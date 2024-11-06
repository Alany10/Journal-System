package JournalSystem.service;

import JournalSystem.model.Patient;
import JournalSystem.repository.IPatientRepository;
import JournalSystem.service.interfaces.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IPatientService {

    private final IPatientRepository patientRepository;

    @Autowired
    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll(); // Använder den automatiska metoden från JpaRepository
    }

    @Override
    public Patient getPatientById(int id) {
        return patientRepository.findById(id).orElse(null); // Returnerar patient eller null om inte hittad
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient); // Sparar en ny patient
    }

    @Override
    public Patient updatePatient(int id, Patient patient) {
<<<<<<< Updated upstream
        if (patientRepository.existsById(id)) {
            patient.setId(id);
            return patientRepository.save(patient);
        }
        return null;
=======
        if (!patientRepository.existsById(id)) throw new IllegalArgumentException("Patient with id " + id + " does not exist");

        patient.setId(id);

        return patientRepository.save(patient);
>>>>>>> Stashed changes
    }

    @Override
    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return patientRepository.existsById(id);
    }
}
