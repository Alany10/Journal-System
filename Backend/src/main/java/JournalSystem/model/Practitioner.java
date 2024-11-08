package JournalSystem.model;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Entity(name = "Practitioner")
@Table(name = "practitioners")
@Getter
@Setter
@NoArgsConstructor
public class Practitioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private int id;

    @Column(name = "first_name",nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name",nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "phone_nr",nullable = false, columnDefinition = "TEXT")
    private String phoneNr;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private List<Encounter> encounters;

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private List<Observation> observations;

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private List<Diagnos> diagnoses;

    public Practitioner(int id, String firstName, String lastName, String phoneNr, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
        this.role = role;
        this.encounters = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
    }

    public Practitioner(String firstName, String lastName, String phoneNr, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
        this.role = role;
        this.encounters = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Practitioner { " +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                " }";
    }
}
