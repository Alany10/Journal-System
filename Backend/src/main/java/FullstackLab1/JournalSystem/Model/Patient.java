package FullstackLab1.JournalSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor // Genererar en standardkonstruktor
@AllArgsConstructor // Genererar en konstruktor med alla fält

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;
    private String lastName;
    private Long phoneNr;
    private String email;
    private String address;


    @Override
    public String toString() {
        return "Patient{" + Id +
                ", " + name +
                ", email:" + email +
                ", Address:" + address +
                '}';
    }
}
