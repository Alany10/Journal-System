package JournalSystem.service;

import JournalSystem.model.Patient;
import JournalSystem.repository.IPatientRepository;
import JournalSystem.service.interfaces.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {

    private final IPatientRepository patientRepository;

    @Autowired
    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(int id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(int id, Patient patient) {
        if (patientRepository.existsById(id)) {
            patient.setId(id);
            return patientRepository.save(patient);
        }
        return null;
    }

    @Override
    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return patientRepository.existsById(id);
    }

    public boolean verifyLogin(String email, String password) {
        Optional<Patient> patientOptional = patientRepository.findByEmailAndPassword(email, password);
        return patientOptional.isPresent();
    }

    public int getIdByEmail(String email) {
        return patientRepository.getIdByEmail(email);
    }

}
