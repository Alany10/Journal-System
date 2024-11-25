package journalSystem.repository;

import journalSystem.model.Message;
import journalSystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Integer> {
    // Hämta mottagna meddelanden för en patient från en lista av roller
    List<Message> findByPatient_IdAndSenderIn(int patientId, List<Role> roles);

    // Hämta mottagna meddelanden för en praktiker från patienter
    List<Message> findByPractitioner_IdAndSender(int practitionerId, Role role);

    // Hämta olästa mottagna meddelanden för en patient från en lista av roller
    List<Message> findByIsReadFalseAndPatient_IdAndSenderIn(int patientId, List<Role> roles);

    // Hämta olästa mottagna meddelanden för en praktiker från patienter
    List<Message> findByIsReadFalseAndPractitioner_IdAndSender(int practitionerId, Role role);

    // Hämta skickade meddelanden från en patient
    List<Message> findByPatient_IdAndSender(int patientId, Role role);

    // Hämta skickade meddelanden från en praktiker till roller (doktor eller annan)
    List<Message> findByPractitioner_IdAndSenderIn(int practitionerId, List<Role> roles);
}
