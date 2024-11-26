package message.repository;

import message.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Integer> {
    // Hämta skickade meddelanden för en user
    List<Message> findBySender(String sender);

    // Hämta mottagna meddelanden för en user
    List<Message> findByReceiver(String receiver);

    // Hämta olästa mottagna meddelanden för en user
    List<Message> findByIsReadFalseAndReceiver(String receiver);
}
