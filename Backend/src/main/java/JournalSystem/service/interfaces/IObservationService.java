package JournalSystem.service.interfaces;

import JournalSystem.model.Observation;
import JournalSystem.model.Patient;
import JournalSystem.model.Practitioner;

import java.util.List;

public interface IObservationService {
    List<Observation> getAllObservations();
    Observation getObservationById(int id);
    Observation createObservation(Observation observation);
    Observation updateObservation(int id, Observation observation);
    void deleteObservation(int id);
    List<Observation> getAllObservationsByPatient(Patient patient);
    List<Observation> getAllObservationsByPractitioner(Practitioner practitioner);
    List<Observation> getAllObservationsByDiagnos(int diagnosId);
    boolean existsById(int id);
}
