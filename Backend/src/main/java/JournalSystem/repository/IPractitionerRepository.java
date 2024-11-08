package JournalSystem.repository;

import JournalSystem.model.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPractitionerRepository extends JpaRepository<Practitioner, Integer> {
}
