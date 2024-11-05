package FullstackLab1.JournalSystem.service.interfaces;

import FullstackLab1.JournalSystem.model.Staff;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IStaffService {
    List<Staff> getAllStaffs();
    Staff getStaffById(int id);
    Staff createStaff(Staff staff);
    Staff updateStaff(int id, Staff staff);
    void deleteStaff(int id);
}
