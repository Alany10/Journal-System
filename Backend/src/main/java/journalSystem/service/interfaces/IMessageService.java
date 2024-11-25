package journalSystem.service.interfaces;

import journalSystem.model.Message;
import journalSystem.model.Role;

import java.util.List;

public interface IMessageService {
    List<Message> getAllMessages();
    Message getMessageById(int id);
    Message createMessage(Message message);
    Message updateMessage(int id, Message message);
    void deleteMessage(int id);

    List<Message> getAllReceivedById(int id, Role role);

    List<Message> getAllUnreadById(int id, Role role);

    List<Message> getAllSentById(int id, Role role);

    boolean existsById(int id);
}

