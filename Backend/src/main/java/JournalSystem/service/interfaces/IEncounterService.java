package JournalSystem.service.interfaces;

import JournalSystem.model.Encounter;
import JournalSystem.model.Practitioner;
import JournalSystem.model.Patient;

import java.util.List;

public interface IEncounterService {
    List<Encounter> getAllEncounters();
    Encounter getEncounterById(int id);
    Encounter createEncounter(Encounter encounter);
    Encounter updateEncounter(int id, Encounter encounter);
    void deleteEncounter(int id);
    List<Encounter> getAllEncountersByPatient(Patient patient);
    List<Encounter> getAllEncountersByPractitioner(Practitioner practitioner);
    boolean existsById(int id);
}
