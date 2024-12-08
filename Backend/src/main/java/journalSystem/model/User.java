package journalSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "TEXT")
    private String email;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "phone_nr", nullable = false, columnDefinition = "TEXT")
    private String phoneNr;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    // En lista för alla encounters kopplade till denna användare (både patient och praktiker)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Encounter> patientEncounters = new ArrayList<>();

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private List<Encounter> practitionerEncounters = new ArrayList<>();

    // En lista för alla observationer kopplade till denna användare (både patient och praktiker)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Observation> patientObservations = new ArrayList<>();

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private List<Observation> practitionerObservations = new ArrayList<>();

    // En lista för alla diagnoser kopplade till denna användare (både patient och praktiker)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Diagnos> patientDiagnoses = new ArrayList<>();

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private List<Diagnos> practitionerDiagnoses = new ArrayList<>();

    // Konstruktorer
    public User(int id, String email, String firstName, String lastName, String phoneNr, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
        this.role = role;
        this.patientEncounters = new ArrayList<>();
        this.practitionerEncounters = new ArrayList<>();
        this.patientObservations = new ArrayList<>();
        this.practitionerObservations = new ArrayList<>();
        this.patientDiagnoses = new ArrayList<>();
        this.practitionerDiagnoses = new ArrayList<>();
    }

    // Konstruktorer
    public User(String email, String firstName, String lastName, String phoneNr, Role role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
        this.role = role;
        this.patientEncounters = new ArrayList<>();
        this.practitionerEncounters = new ArrayList<>();
        this.patientObservations = new ArrayList<>();
        this.practitionerObservations = new ArrayList<>();
        this.patientDiagnoses = new ArrayList<>();
        this.practitionerDiagnoses = new ArrayList<>();
    }
}
