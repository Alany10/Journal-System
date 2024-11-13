package JournalSystem.repository;

import JournalSystem.model.Practitioner;
import JournalSystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPractitionerRepository extends JpaRepository<Practitioner, Integer> {
    Optional<Practitioner> findByEmailAndPasswordAndRole(String email, String password, Role role);
    @Query("SELECT p.id FROM Practitioner p WHERE p.email = :email")
    int getIdByEmail(String email);
}
