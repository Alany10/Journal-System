package JournalSystem.repository;

import JournalSystem.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IObservationRepository extends JpaRepository<Observation, Integer> {
    List<Observation> findByPatient_Id(int patient_id);
    List<Observation> findByPractitioner_Id(int practitioner_id);
    List<Observation> findByDiagnos_Id(int diagnos_id);
}
