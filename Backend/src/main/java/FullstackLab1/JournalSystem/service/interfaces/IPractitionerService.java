package FullstackLab1.JournalSystem.service.interfaces;

import FullstackLab1.JournalSystem.model.Practitioner;

import java.util.List;

public interface IPractitionerService {
    List<Practitioner> getAllPractitioner();
    Practitioner getPractitionerById(int id);
    Practitioner createPractitioner(Practitioner practitioner);
    Practitioner updatePractitioner(int id, Practitioner practitioner);
    void deletePractitioner(int id);
}
