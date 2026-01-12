package com.diploma.riskprediction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GradeDataService {

    // TODO: Реализовать gRPC клиент для grade-service
    // Пока возвращаем заглушку
    public Double getAverageGrade(UUID studentId) {
        log.warn("Using stub for grade data. StudentId: {}", studentId);
        // Заглушка - в реальности будет gRPC вызов к grade-service
        return null;
    }
}
