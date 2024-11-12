package JournalSystem.service;

import JournalSystem.model.Patient;
import JournalSystem.model.Practitioner;
import JournalSystem.repository.IPractitionerRepository;
import JournalSystem.service.interfaces.IPractitionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PractitionerService implements IPractitionerService {

    private final IPractitionerRepository practitionerRepository;

    @Autowired
    public PractitionerService(IPractitionerRepository PractitionerRepository) {
        this.practitionerRepository = PractitionerRepository;
    }

    @Override
    public List<Practitioner> getAllPractitioner() {
        return practitionerRepository.findAll();
    }

    @Override
    public Practitioner getPractitionerById(int id) {
        return practitionerRepository.findById(id).orElse(null);
    }

    @Override
    public Practitioner createPractitioner(Practitioner practitioner) {
        return practitionerRepository.save(practitioner);
    }

    @Override
    public Practitioner updatePractitioner(int id, Practitioner practitioner) {
        if (practitionerRepository.existsById(id)) {
            practitioner.setId(id);
            return practitionerRepository.save(practitioner);
        }
        return null;
    }

    @Override
    public void deletePractitioner(int id) {
        practitionerRepository.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return practitionerRepository.existsById(id);
    }

    public boolean verifyLogin(String email, String password) {
        Optional<Practitioner> practitionerOptional = practitionerRepository.findByEmailAndPassword(email, password);
        return practitionerOptional.isPresent();
    }

    public int getIdByEmail(String email) {
        return practitionerRepository.getIdByEmail(email);
    }
}
