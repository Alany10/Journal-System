package JournalSystem.model;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "sender", nullable = false)
    private Role sender;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "practitioner_id", nullable = false)
    private User practitioner;

    public Message(int id, String title, String text, Role sender, User patient, User practitioner) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.dateTime = LocalDateTime.now();
        this.sender = sender;
        this.isRead = false;
        this.patient = patient;
        this.practitioner = practitioner;
    }

    public Message(String title, String text, Role sender, User patient, User practitioner) {
        this.title = title;
        this.text = text;
        this.dateTime = LocalDateTime.now();
        this.isRead = false;
        this.sender = sender;
        this.patient = patient;
        this.practitioner = practitioner;
    }

    //TODO
    public boolean getIsRead(){
        return isRead;
    }
}