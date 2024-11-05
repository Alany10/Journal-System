package FullstackLab1.JournalSystem.service.interfaces;

import FullstackLab1.JournalSystem.model.Patient;

import java.util.List;

public interface IPatientService {
    List<Patient> getAllPatients();
    Patient getPatientById(int id);
    Patient createPatient(Patient patient);
    Patient updatePatient(int id, Patient patient);
    void deletePatient(int id);
}
