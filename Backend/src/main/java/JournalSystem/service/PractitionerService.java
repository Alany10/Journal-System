package JournalSystem.service;

import JournalSystem.model.Practitioner;
import JournalSystem.repository.IPractitionerRepository;
import JournalSystem.service.interfaces.IPractitionerService;
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
        return null;
    }

    @Override
    public void deletePractitioner(int id) {
        PractitionerRepository.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return practitionerRepository.existsById(id);
    }
}
