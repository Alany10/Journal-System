package message.service;

import message.model.Message;
import message.repository.IMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService{
    private final IMessageRepository messageRepository;

    @Autowired
    public MessageService(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        Message message = messageRepository.findById(id).orElse(null);

        if (message == null) throw new IllegalArgumentException("Message with id " + id + " does not exist");

        return message;
    }

    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    public Message updateMessage(int id, Message message){
        if (!messageRepository.existsById(id)) throw new IllegalArgumentException("Message with id " + id + " does not exist");

        message.setId(id);
        return messageRepository.save(message);
    }

    public void deleteMessage(int id){
        Message message = messageRepository.findById(id).orElse(null);

        if (message == null) throw new IllegalArgumentException("Message with id " + id + " does not exist");

        messageRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return messageRepository.existsById(id);
    }

    public List<Message> getAllReceivedByEmail(String email) {
        return messageRepository.findByReceiver(email);
    }

    public List<Message> getAllUnreadByEmail(String email) {
        return messageRepository.findByIsReadFalseAndReceiver(email);
    }

    public List<Message> getAllSentByEmail(String email) {
        return messageRepository.findBySender(email);
    }
}
