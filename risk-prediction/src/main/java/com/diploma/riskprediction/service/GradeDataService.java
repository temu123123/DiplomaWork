package com.diploma.riskprediction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GradeDataService {

    private final com.diploma.riskprediction.client.GradeGrpcClient gradeGrpcClient;

    public Double getAverageGrade(UUID studentId) {
        log.info("Fetching grade data via gRPC for student: {}", studentId);
        try {
            var stats = gradeGrpcClient.getGradeStats(studentId);
            return stats.getAverageGrade();
        } catch (Exception e) {
            log.error("Failed to fetch grade data: {}", e.getMessage());
            return null;
        }
    }
}
