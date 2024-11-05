package FullstackLab1.JournalSystem.service.interfaces;

import FullstackLab1.JournalSystem.model.Doctor;

import java.util.List;


public interface IDoctorService {
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(int id);
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(int id, Doctor doctor);
    void deleteDoctor(int id);
}
