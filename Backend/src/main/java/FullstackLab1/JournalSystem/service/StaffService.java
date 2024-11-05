package FullstackLab1.JournalSystem.service;

import FullstackLab1.JournalSystem.model.Staff;
import FullstackLab1.JournalSystem.repository.IStaffRepository;
import FullstackLab1.JournalSystem.service.interfaces.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService implements IStaffService {

    private final IStaffRepository staffRepository;

    @Autowired
    public StaffService(IStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public List<Staff> getAllStaffs() {
        return staffRepository.findAll(); // Använder den automatiska metoden från JpaRepository
    }

    @Override
    public Staff getStaffById(int id) {
        return staffRepository.findById(id).orElse(null); // Returnerar patient eller null om inte hittad
    }

    @Override
    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff); // Sparar en ny patient
    }

    @Override
    public Staff updateStaff(int id, Staff staff) {
        // Kolla om patienten finns och uppdatera om så är fallet
        if (staffRepository.existsById(id)) {
            staff.setId(id); // Ställ in ID för att uppdatera rätt patient
            return staffRepository.save(staff);
        }
        return null; // Returnera null om patienten inte finns
    }

    @Override
    public void deleteStaff(int id) {
        staffRepository.deleteById(id); // Tar bort patienten baserat på ID
    }
}
