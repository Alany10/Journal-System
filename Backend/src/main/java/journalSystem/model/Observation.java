package journalSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Observation")
@Table(name = "observation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "practitioner_id", nullable = false)
    private User practitioner;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne
    @JoinColumn(name = "diagnos_id", nullable = false)
    private Diagnos diagnos;

    public Observation(String description, User patient, User practitioner, Encounter encounter, Diagnos diagnos) {
        this.description = description;
        this.patient = patient;
        this.practitioner = practitioner;
        this.encounter = encounter;
        this.diagnos = diagnos;
    }

}