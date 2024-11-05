package FullstackLab1.JournalSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table; // Importerar Table om du vill ange en specifik tabell
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "doctor") // Specifierar tabellnamnet i databasen, om du vill
@Getter
@Setter
@NoArgsConstructor // Genererar en standardkonstruktor
@AllArgsConstructor // Genererar en konstruktor med alla fält
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNr;

    @Override
    public String toString() {
        return "Doctor { " +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                " }";
    }
}
