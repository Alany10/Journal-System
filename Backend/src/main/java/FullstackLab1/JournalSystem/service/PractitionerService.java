package FullstackLab1.JournalSystem.service;

import FullstackLab1.JournalSystem.model.Practitioner;
import FullstackLab1.JournalSystem.repository.IPractitionerRepository;
import FullstackLab1.JournalSystem.service.interfaces.IPractitionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PractitionerService implements IPractitionerService {

    private final IPractitionerRepository PractitionerRepository;

    @Autowired
    public PractitionerService(IPractitionerRepository PractitionerRepository) {
        this.PractitionerRepository = PractitionerRepository;
    }

    @Override
    public List<Practitioner> getAllPractitioner() {
        return PractitionerRepository.findAll();
    }

    @Override
    public Practitioner getPractitionerById(int id) {
        return PractitionerRepository.findById(id).orElse(null);
    }

    @Override
    public Practitioner createPractitioner(Practitioner practitioner) {
        return PractitionerRepository.save(practitioner);
    }

    @Override
    public Practitioner updatePractitioner(int id, Practitioner practitioner) {
        if (PractitionerRepository.existsById(id)) {
            practitioner.setId(id);
            return PractitionerRepository.save(practitioner);
        }
        return null; // Returnera null om patienten inte finns
    }

    @Override
    public void deleteStaff(int id) {
        staffRepository.deleteById(id); // Tar bort patienten baserat på ID
    }
}
