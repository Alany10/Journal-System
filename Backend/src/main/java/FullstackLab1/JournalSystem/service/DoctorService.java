package FullstackLab1.JournalSystem.service;

import FullstackLab1.JournalSystem.model.Doctor;
import FullstackLab1.JournalSystem.repository.IDoctorRepository;
import FullstackLab1.JournalSystem.service.interfaces.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService implements IDoctorService {

    private final IDoctorRepository doctorRepository;

    @Autowired
    public DoctorService(IDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll(); // Använder den automatiska metoden från JpaRepository
    }

    @Override
    public Doctor getDoctorById(int id) {
        return doctorRepository.findById(id).orElse(null); // Returnerar patient eller null om inte hittad
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(int id, Doctor doctor) {
        // Kolla om doktorn finns och uppdatera om så är fallet
        if (doctorRepository.existsById(id)) {
            doctor.setId(id); // Ställ in ID för att uppdatera rätt doctor
            return doctorRepository.save(doctor);
        }
        return null; // Returnera null om doktorn inte finns
    }

    @Override
    public void deleteDoctor(int id) {
        doctorRepository.deleteById(id); // Tar bort patienten baserat på ID
    }
}
