package journalSystem.service.interfaces;

import journalSystem.model.Encounter;

import java.util.List;

public interface IEncounterService {
    List<Encounter> getAllEncounters();
    Encounter getEncounterById(int id);
    Encounter createEncounter(Encounter encounter);
    Encounter updateEncounter(int id, Encounter encounter);
    void deleteEncounter(int id);
    List<Encounter> getAllEncountersByPatientId(int patientId);
    List<Encounter> getAllEncountersByPractitionerId(int practitionerId);
    boolean existsById(int id);
}
