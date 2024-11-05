package FullstackLab1.JournalSystem.repository;

import FullstackLab1.JournalSystem.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor,Integer> {
}
