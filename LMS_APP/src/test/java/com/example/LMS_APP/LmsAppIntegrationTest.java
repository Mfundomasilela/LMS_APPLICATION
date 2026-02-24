package com.example.LMS_APP;

import com.example.LMS_APP.entity.LeaveRequest;
import com.example.LMS_APP.repository.LeaveRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LmsAppIntegrationTest {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Test
    void testSaveLeaveRequest() {
        LeaveRequest request = new LeaveRequest();
        request.setLeaveType("Annual");
        request.setReason("Vacation");

        LeaveRequest saved = leaveRequestRepository.save(request);

        assertNotNull(saved.getId());
    }
}