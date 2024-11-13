package JournalSystem.service.interfaces;

import JournalSystem.model.Patient;
import JournalSystem.model.Role;

import java.util.List;

public interface IPatientService {
    List<Patient> getAllPatients();
    Patient getPatientById(int id);
    Patient createPatient(Patient patient);
    Patient updatePatient(int id, Patient patient);
    void deletePatient(int id);
    boolean existsById(int id);
    boolean verifyLogin(String email, String password, Role role);
    int getIdByEmail(String email);
}
