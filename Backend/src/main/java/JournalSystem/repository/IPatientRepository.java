package JournalSystem.repository;

import JournalSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByEmailAndPassword(String email, String password);
    @Query("SELECT p.id FROM Patient p WHERE p.email = :email")
    int getIdByEmail(String email);
}