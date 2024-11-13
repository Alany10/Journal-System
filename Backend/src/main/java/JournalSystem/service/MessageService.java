package JournalSystem.service;

import JournalSystem.model.Message;
import JournalSystem.model.Sender;
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
    public List<Message> getAllUnreadReceivedByPatientId(int patientId){
        return messageRepository.findByIsReadFalseAndPatient_IdAndSender(patientId, Sender.PRACTITIONER);
    }

    @Override
    public List<Message> getAllUnreadReceivedByPractitionerId(int practitionerId){
        return messageRepository.findByIsReadFalseAndPractitioner_IdAndSender(practitionerId, Sender.PATIENT);
    }

    @Override
    public List<Message> getAllSentByPatientId(int patientId){
        return messageRepository.findByPatient_IdAndSender(patientId, Sender.PATIENT);
    }

    @Override
    public List<Message> getAllSentByPractitionerId(int practitionerId){
        return messageRepository.findByPractitioner_IdAndSender(practitionerId, Sender.PRACTITIONER);
    }

    @Override
    public List<Message> getAllReceivedByPatientId(int patientId){
        return messageRepository.findByPatient_IdAndSender(patientId, Sender.PRACTITIONER);
    }

    @Override
    public List<Message> getAllReceivedByPractitionerId(int practitionerId){
        return messageRepository.findByPractitioner_IdAndSender(practitionerId, Sender.PATIENT);
    }

    @Override
    public boolean existsById(int id) {
        return messageRepository.existsById(id);
    }

}
