package journalSystem.repository;

import journalSystem.model.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IEncounterRepository extends JpaRepository<Encounter, Integer> {
    List<Encounter> findByPatient_Id(int patientId);
    List<Encounter> findByPractitioner_Id(int practitionerId);
}
