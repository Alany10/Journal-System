package JournalSystem.service.interfaces;

import JournalSystem.model.Message;

import java.util.List;

public interface IMessageService {
    List<Message> getAllMessages();
    Message getMessageById(int id);
    Message createMessage(Message message);
    Message updateMessage(int id, Message message);
    void deleteMessage(int id);

    List<Message> getAllUnreadReceivedByPatientId(int patientId);
    List<Message> getAllUnreadReceivedByPractitionerId(int practitionerId);

    List<Message> getAllSentByPatientId(int patientId);
    List<Message> getAllSentByPractitionerId(int practitionerId);

    List<Message> getAllReceivedByPatientId(int patientId);
    List<Message> getAllReceivedByPractitionerId(int practitionerId);

    boolean existsById(int id);
}

