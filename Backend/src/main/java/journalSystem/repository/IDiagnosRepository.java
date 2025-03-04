package journalSystem.repository;

import journalSystem.model.Diagnos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiagnosRepository extends JpaRepository<Diagnos,Integer> {
    List<Diagnos> findByPatient_Id(int patientId);
    List<Diagnos> findByPractitioner_Id(int practitionerId);
}
