package FullstackLab1.JournalSystem.repository;


import FullstackLab1.JournalSystem.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStaffRepository extends JpaRepository<Staff, Integer> {
}
