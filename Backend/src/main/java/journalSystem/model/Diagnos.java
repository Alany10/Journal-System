package journalSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Diagnos")
@Table(name = "diagnos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Diagnos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "diagnos_status", nullable = false)
    private DiagnosStatus diagnosStatus;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "practitioner_id", nullable = false)
    private User practitioner;

    @OneToMany(mappedBy = "diagnos", cascade = CascadeType.ALL)
    private List<Observation> observations;

    public Diagnos(int id, String name, User patient, User practitioner) {
        this.id = id;
        this.name = name;
        this.diagnosStatus = DiagnosStatus.ONGOING;
        this.patient = patient;
        this.practitioner = practitioner;
        this.observations = new ArrayList<>();
    }

    public Diagnos(String name, User patient, User practitioner) {
        this.name = name;
        this.diagnosStatus = DiagnosStatus.ONGOING;
        this.patient = patient;
        this.practitioner = practitioner;
        this.observations = new ArrayList<>();
    }

    public Diagnos(int id, String name, DiagnosStatus status, User patient, User practitioner) {
        this.id = id;
        this.name = name;
        this.diagnosStatus = status;
        this.patient = patient;
        this.practitioner = practitioner;
        this.observations = new ArrayList<>();
    }
}