package message.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Message")
@Table(name = "message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "sender", nullable = false)
    private String sender;

    @Column(name = "receiver", nullable = false)
    private String receiver;

    public Message(int id, String title, String text, String sender, String receiver) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.dateTime = LocalDateTime.now();
        this.isRead = false;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Message(String title, String text, String sender, String receiver) {
        this.title = title;
        this.text = text;
        this.dateTime = LocalDateTime.now();
        this.isRead = false;
        this.sender = sender;
        this.receiver = receiver;
    }

    //TODO
    public boolean getIsRead() {return isRead;
    }
}
