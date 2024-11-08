package JournalSystem.repository;

import JournalSystem.model.Encounter;
import JournalSystem.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEncounterRepository extends JpaRepository<Encounter, Integer> {
    List<Encounter> findByPatient_Id(int patientId);
    List<Encounter> findByPractitioner_Id(int practitioner_id);
}
