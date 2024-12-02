package search.service;

import io.smallrye.mutiny.Uni;
import search.model.Diagnos;
import search.model.Encounter;
import search.model.Observation;
import search.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import search.repository.DiagnosRepository;
import search.repository.EncounterRepository;
import search.repository.ObservationRepository;
import search.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class SearchService {

    private final UserRepository userRepository;
    private final DiagnosRepository diagnosRepository;
    private final EncounterRepository encounterRepository;
    private final ObservationRepository observationRepository;

    public SearchService(UserRepository userRepository, DiagnosRepository diagnosRepository, EncounterRepository encounterRepository, ObservationRepository observationRepository) {
        this.userRepository = userRepository;
        this.diagnosRepository = diagnosRepository;
        this.encounterRepository = encounterRepository;
        this.observationRepository = observationRepository;
    }

    // Hitta alla patienter som tillhör en läkare (genom diagnoser)
    public Uni<List<User>> findPatientsByDoctor(User doctor) {
        return userRepository.findPatientsByDoctor(doctor);
    }

    // Hitta patienter baserat på namn
    public Uni<List<User>> findPatientsByName(String name) {
        return userRepository.findPatientsByName(name);
    }

    // Hitta diagnoser baserat på patient
    public Uni<List<Diagnos>> findDiagnosesByPatient(User patient) {
        return diagnosRepository.findDiagnosesByPatient(patient);
    }

    // Hitta diagnoser baserat på status
    public Uni<List<Diagnos>> findDiagnosesByStatus(String status) {
        return diagnosRepository.findDiagnosesByStatus(status);
    }

    // Hitta diagnoser för en viss läkare
    public Uni<List<Diagnos>> findDiagnosesByPractitioner(User practitioner) {
        return diagnosRepository.findDiagnosesByPractitioner(practitioner);
    }

    // Hitta encounters för en läkare under ett specifikt datum
    public Uni<List<Encounter>> findEncountersByPractitionerAndDate(User practitioner, LocalDateTime date) {
        return encounterRepository.findEncountersByPractitionerAndDate(practitioner, date);
    }

    // Hitta encounters för en viss patient
    public Uni<List<Encounter>> findEncountersByPatient(User patient) {
        return encounterRepository.findEncountersByPatient(patient);
    }

    // Hitta observationer för en diagnos
    public Uni<List<Observation>> findObservationsByDiagnos(Diagnos diagnos) {
        return observationRepository.findObservationsByDiagnos(diagnos);
    }

    // Hitta alla läkare
    public Uni<List<User>> findDoctors() {
        return userRepository.findDoctors();
    }

    // Hitta patienter baserat på diagnos
    public Uni<List<User>> findPatientsByDiagnos(Diagnos diagnos) {
        return userRepository.findPatientsByDiagnos(diagnos);
    }

    // Hitta patienter baserat på encounter
    public Uni<List<User>> findPatientsByEncounter(Encounter encounter) {
        return userRepository.findPatientsByEncounter(encounter);
    }
}
