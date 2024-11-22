package JournalSystem.service;

import JournalSystem.model.Message;
import JournalSystem.model.Role;
import JournalSystem.repository.IMessageRepository;
import JournalSystem.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IMessageService {
    private final IMessageRepository messageRepository;

    @Autowired
    public MessageService(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    @Override
    public Message getMessageById(int id) {
        Message message = messageRepository.findById(id).orElse(null);

        if (message == null) throw new IllegalArgumentException("Message with id " + id + " does not exist");

        return message;
    }

    @Override
    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    @Override
    public Message updateMessage(int id, Message message){
        if (!messageRepository.existsById(id)) throw new IllegalArgumentException("Message with id " + id + " does not exist");

        message.setId(id);
        return messageRepository.save(message);
    }

    @Override
    public void deleteMessage(int id){
        Message message = messageRepository.findById(id).orElse(null);

        if (message == null) throw new IllegalArgumentException("Message with id " + id + " does not exist");

        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> getAllReceivedById(int id, Role sender) {
        if (sender == Role.PATIENT) {
            return messageRepository.findByPatient_IdAndSenderIn(id, List.of(Role.DOCTOR, Role.OTHER));
        } else {
            return messageRepository.findByPractitioner_IdAndSender(id, Role.PATIENT);
        }
    }


    @Override
    public List<Message> getAllUnreadById(int id, Role sender) {
        if (sender == Role.PATIENT) {
            return messageRepository.findByIsReadFalseAndPatient_IdAndSenderIn(id, List.of(Role.DOCTOR, Role.OTHER));
        } else {
            return messageRepository.findByIsReadFalseAndPractitioner_IdAndSender(id, Role.PATIENT);
        }
    }

    @Override
    public List<Message> getAllSentById(int id, Role sender) {
        if (sender == Role.PATIENT) {
            return messageRepository.findByPatient_IdAndSender(id, Role.PATIENT);
        } else {
            return messageRepository.findByPractitioner_IdAndSenderIn(id, List.of(Role.DOCTOR, Role.OTHER));
        }
    }


    @Override
    public boolean existsById(int id) {
        return messageRepository.existsById(id);
    }

}
