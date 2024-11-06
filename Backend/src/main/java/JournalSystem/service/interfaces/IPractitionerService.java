package JournalSystem.service.interfaces;

import JournalSystem.model.Practitioner;

import java.util.List;

public interface IPractitionerService {
    List<Practitioner> getAllPractitioner();
    Practitioner getPractitionerById(int id);
    Practitioner createPractitioner(Practitioner practitioner);
    Practitioner updatePractitioner(int id, Practitioner practitioner);
    void deletePractitioner(int id);
    boolean existsById(int id);
}
