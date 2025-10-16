package com.employeeManagement.demo.Service;

import com.employeeManagement.demo.Model.Leave;
import com.employeeManagement.demo.Repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;
    public LeaveService(LeaveRepository leaveRepository){
        this.leaveRepository=leaveRepository;
    }

    public List<Leave> getPendingRequests() {
        return leaveRepository.findByStatus("Pending");
    }

    public void approveLeave(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow();
        leave.setStatus("Approved");
        leaveRepository.save(leave);
    }

    public void rejectLeave(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow();
        leave.setStatus("Rejected");
        leaveRepository.save(leave);
    }

    public List<Leave> getAllRequests() {
        return leaveRepository.findAll();
    }
}
