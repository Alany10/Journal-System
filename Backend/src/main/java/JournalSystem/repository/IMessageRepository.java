package JournalSystem.repository;

import JournalSystem.model.Message;
import JournalSystem.model.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Integer> {
   // 1. Hämta meddelanden där patienten är avsändaren
    List<Message> findByPatient_IdAndSender(int patientId, Sender sender);

    // 2. Hämta meddelanden där praktiker är avsändaren
    List<Message> findByPractitioner_IdAndSender(int practitionerId, Sender sender);

    // 3. Hämta olästa meddelanden där patienten är avsändaren
    List<Message> findByIsReadFalseAndPatient_IdAndSender(int patientId, Sender sender);

    // 4. Hämta olästa meddelanden där praktiker är avsändaren
    List<Message> findByIsReadFalseAndPractitioner_IdAndSender(int practitionerId, Sender sender);
}
