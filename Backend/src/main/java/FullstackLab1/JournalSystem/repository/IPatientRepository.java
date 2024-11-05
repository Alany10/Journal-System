package FullstackLab1.JournalSystem.repository;

import FullstackLab1.JournalSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {
    public List<Patient> findByPhoneNr(String phoneNr);
}
