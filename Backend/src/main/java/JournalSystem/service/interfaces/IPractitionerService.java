package JournalSystem.service.interfaces;

import JournalSystem.model.Practitioner;
import JournalSystem.model.Role;

import java.util.List;

public interface IPractitionerService {
    List<Practitioner> getAllPractitioner();
    Practitioner getPractitionerById(int id);
    Practitioner createPractitioner(Practitioner practitioner);
    Practitioner updatePractitioner(int id, Practitioner practitioner);
    void deletePractitioner(int id);
    boolean existsById(int id);
    boolean verifyLogin(String email, String password, Role role);
    int getIdByEmail(String email);
}
