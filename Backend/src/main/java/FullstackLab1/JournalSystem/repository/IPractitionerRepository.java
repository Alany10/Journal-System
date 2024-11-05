package FullstackLab1.JournalSystem.repository;


import FullstackLab1.JournalSystem.model.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPractitionerRepository extends JpaRepository<Practitioner, Integer> {
}
