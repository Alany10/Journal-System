package FullstackLab1.JournalSystem.service;

import FullstackLab1.JournalSystem.model.Patient;
import FullstackLab1.JournalSystem.repository.IPatientRepository;
import FullstackLab1.JournalSystem.service.interfaces.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IPatientService {

    private final IPatientRepository patientRepository;

    @Autowired
    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll(); // Använder den automatiska metoden från JpaRepository
    }

    @Override
    public Patient getPatientById(int id) {
        return patientRepository.findById(id).orElse(null); // Returnerar patient eller null om inte hittad
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient); // Sparar en ny patient
    }

    @Override
    public Patient updateStaff(int id, Patient patient) {
        // Kolla om patienten finns och uppdatera om så är fallet
        if (patientRepository.existsById(id)) {
            patient.setId(id); // Ställ in ID för att uppdatera rätt patient
            return patientRepository.save(patient);
        }
        return null; // Returnera null om patienten inte finns
    }

    @Override
    public void deletePatient(int id) {
        patientRepository.deleteById(id); // Tar bort patienten baserat på ID
    }
}
