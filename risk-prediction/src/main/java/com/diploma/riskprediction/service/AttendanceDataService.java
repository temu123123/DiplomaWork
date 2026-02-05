package com.diploma.riskprediction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttendanceDataService {

    private final com.diploma.riskprediction.client.AttendanceGrpcClient attendanceGrpcClient;

    public Long getUnexcusedCount(UUID studentId) {
        log.info("Fetching attendance data via gRPC for student: {}", studentId);
        try {
            var stats = attendanceGrpcClient.getAttendanceStats(studentId);
            return (long) stats.getUnexcusedAbsences();
        } catch (Exception e) {
            log.error("Failed to fetch attendance data: {}", e.getMessage());
            return 0L; // Fallback
        }
    }
}
