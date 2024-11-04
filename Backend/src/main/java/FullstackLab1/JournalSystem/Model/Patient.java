package FullstackLab1.JournalSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;
    private String lastName;
    private Long phoneNr;
    private String email;
    private String address;

    public Patient() {}

    public Patient(int id, String name, String lastName, Long phoneNr, String email, String address) {
        Id = id;
        this.name = name;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getPhoneNr() {
        return phoneNr;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNr(Long phoneNr) {
        this.phoneNr = phoneNr;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patient{" + Id +
                ", " + name +
                ", email:" + email +
                ", Address:" + address +
                '}';
    }
}
