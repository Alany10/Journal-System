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

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

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
    public User(int id, String email, String name, String password, String phoneNr, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNr = phoneNr;
        this.role = role;
        this.patientEncounters = new ArrayList<>();
        this.practitionerEncounters = new ArrayList<>();
        this.patientObservations = new ArrayList<>();
        this.practitionerObservations = new ArrayList<>();
        this.patientDiagnoses = new ArrayList<>();
        this.practitionerDiagnoses = new ArrayList<>();
    }

    public User(String email, String name, String password, String phoneNr, Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
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
