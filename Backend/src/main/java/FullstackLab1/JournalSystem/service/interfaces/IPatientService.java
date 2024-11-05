package FullstackLab1.JournalSystem.service.interfaces;

import FullstackLab1.JournalSystem.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IPatientService {
    List<Patient> getAllPatients(); // Hämta alla patienter
    Patient getPatientById(int id); // Hämta en patient med ett specifikt ID
    Patient createPatient(Patient patient); // Skapa en ny patient
    Patient updateStaff(int id, Patient patient); // Uppdatera en befintlig patient
    void deletePatient(int id); // Ta bort en patient
}
