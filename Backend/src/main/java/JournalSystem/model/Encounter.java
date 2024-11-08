package JournalSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Encounter")
@Table(name = "encounter")
@Getter
@Setter
@NoArgsConstructor
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private int id;
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "practitioner_id", nullable = false)
    private Practitioner practitioner;

    @OneToMany(mappedBy = "encounter", cascade = CascadeType.ALL)
    private List<Observation> observations;

    public Encounter(int id, LocalDateTime dateTime, Patient patient, Practitioner practitioner) {
        this.id = id;
        this.dateTime = dateTime;
        this.patient = patient;
        this.practitioner = practitioner;
        this.observations = new ArrayList<>();
    }

    public Encounter(LocalDateTime dateTime, Patient patient, Practitioner practitioner) {
        this.dateTime = dateTime;
        this.patient = patient;
        this.practitioner = practitioner;
        this.observations = new ArrayList<>();
    }


}
