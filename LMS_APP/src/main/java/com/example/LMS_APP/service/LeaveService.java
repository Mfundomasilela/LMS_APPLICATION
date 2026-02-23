package com.example.LMS_APP.service;

import com.example.LMS_APP.entity.LeaveRequest;
import com.example.LMS_APP.entity.LeaveStatus;
import com.example.LMS_APP.entity.User;
import com.example.LMS_APP.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveService {

    private final LeaveRequestRepository leaveRepo;

    public LeaveService(LeaveRequestRepository leaveRepo) {
        this.leaveRepo = leaveRepo;
    }

    public List<LeaveRequest> employeeLeaves(User employee) {
        return leaveRepo.findByEmployeeOrderByIdDesc(employee);
    }

    public List<LeaveRequest> pendingLeaves() {
        return leaveRepo.findByStatusOrderByIdDesc(LeaveStatus.PENDING);
    }

    @Transactional
    public void applyLeave(User employee, String leaveType, LocalDate start, LocalDate end, String reason) {
        if (leaveType == null || leaveType.isBlank()) throw new IllegalArgumentException("Leave type required");
        if (reason == null || reason.isBlank()) throw new IllegalArgumentException("Reason required");
        if (start == null || end == null) throw new IllegalArgumentException("Dates required");
        if (end.isBefore(start)) throw new IllegalArgumentException("End date cannot be before start date");

        LeaveRequest req = new LeaveRequest();
        req.setEmployee(employee);
        req.setLeaveType(leaveType.trim());
        req.setStartDate(start);
        req.setEndDate(end);
        req.setReason(reason.trim());
        req.setStatus(LeaveStatus.PENDING);

        leaveRepo.save(req);
    }

    @Transactional
    public void approve(Long leaveId) {
        LeaveRequest req = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
        req.setStatus(LeaveStatus.APPROVED);
    }

    @Transactional
    public void reject(Long leaveId) {
        LeaveRequest req = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
        req.setStatus(LeaveStatus.REJECTED);
    }
}
