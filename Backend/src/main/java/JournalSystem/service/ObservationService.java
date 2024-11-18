package JournalSystem.service;

import JournalSystem.model.Observation;
import JournalSystem.model.Patient;
import JournalSystem.model.Practitioner;
import JournalSystem.repository.IObservationRepository;
import JournalSystem.service.interfaces.IObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObservationService implements IObservationService {
    private final IObservationRepository observationRepository;

    @Autowired
    public ObservationService(IObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @Override
    public List<Observation> getAllObservations() {
        return observationRepository.findAll();
    }

    @Override
    public Observation getObservationById(int id) {
        Observation observation = observationRepository.findById(id).orElse(null);

        if (observation == null) throw new IllegalArgumentException("Encounter with id " + id + " does not exist");

        return observation;
    }

    @Override
    public Observation createObservation(Observation observation) {
        return observationRepository.save(observation);
    }

    @Override
    public Observation updateObservation(int id, Observation observation) {
        if (!observationRepository.existsById(id)) throw new IllegalArgumentException("Encounter with id " + id + " does not exist");

        observation.setId(id);
        return observationRepository.save(observation);
    }

    @Override
    public void deleteObservation(int id) {
        Observation observation = observationRepository.findById(id).orElse(null);

        if (observation == null) throw new IllegalArgumentException("Encounter with id " + id + " does not exist");

        observationRepository.deleteById(id);
    }

    @Override
    public List<Observation> getAllObservationsByPatient(Patient patient) {
        if (patient == null) throw new IllegalStateException("Patient is null");

        return observationRepository.findByPatient_Id(patient.getId());
    }

    @Override
    public List<Observation> getAllObservationsByPractitioner(Practitioner practitioner) {
        if (practitioner == null) throw new IllegalStateException("Practitioner is null");

        return observationRepository.findByPractitioner_Id(practitioner.getId());
    }

    @Override
    public List<Observation> getAllObservationsByDiagnos(int diagnosId) {
        if (diagnosId <= 0) throw new IllegalStateException("Patient is null");

        return observationRepository.findByDiagnos_Id(diagnosId);
    }

    @Override
    public boolean existsById(int id) {
        return observationRepository.existsById(id);
    }
}
