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

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "TEXT")
    private String email;

    @Column(name = "name",nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "password",nullable = false, columnDefinition = "TEXT")
    private String password;

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

    public Practitioner(int id, String email, String name, String password, String phoneNr, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNr = phoneNr;
        this.role = role;
        this.encounters = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
    }

    public Practitioner(String email, String name, String password, String phoneNr, Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNr = phoneNr;
        this.role = role;
        this.encounters = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Practitioner{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
