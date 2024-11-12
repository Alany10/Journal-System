package JournalSystem.service;

import JournalSystem.model.Encounter;
import JournalSystem.model.Patient;
import JournalSystem.model.Practitioner;
import JournalSystem.repository.IEncounterRepository;
import JournalSystem.service.interfaces.IEncounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class EncounterService implements IEncounterService {
    private final IEncounterRepository encounterRepository;

    @Autowired
    public EncounterService(IEncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    @Override
    public List<Encounter> getAllEncounters(){
        return encounterRepository.findAll();
    }

    @Override
    public Encounter getEncounterById(int id) {
        Encounter encounter = encounterRepository.findById(id).orElse(null);

        if (encounter == null) throw new IllegalArgumentException("Encounter with id " + id + " does not exist");

        return encounter;
    }


    @Override
    public Encounter createEncounter(Encounter encounter){
        return encounterRepository.save(encounter);
    }

    @Override
    public Encounter updateEncounter(int id, Encounter encounter){
        if (!encounterRepository.existsById(id)) throw new IllegalArgumentException("Encounter with id " + id + " does not exist");

        encounter.setId(id);
        return encounterRepository.save(encounter);
    }


    public void deleteEncounter(int id){
        Encounter encounter = encounterRepository.findById(id).orElse(null);

        if (encounter == null) throw new IllegalArgumentException("Encounter with id " + id + " does not exist");

        encounterRepository.deleteById(id);
    }

    public List<Encounter> getAllEncountersByPatientId(int patientId){
        if (patientId <= 0) throw new IllegalStateException("Patient is null");

        return encounterRepository.findByPatient_Id(patientId);
    }


    public List<Encounter> getAllEncountersByPractitionerId(int practitionerID){
        if (practitionerID <= 0) throw new IllegalStateException("Practitioner is null");

        return encounterRepository.findByPractitioner_Id(practitionerID);
    }

    @Override
    public boolean existsById(int id) {
        return encounterRepository.existsById(id);
    }
}
