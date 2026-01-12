package com.diploma.riskprediction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttendanceDataService {

    // TODO: Реализовать gRPC клиент для attendance-service
    // Пока возвращаем заглушку
    public Long getUnexcusedCount(UUID studentId) {
        log.warn("Using stub for attendance data. StudentId: {}", studentId);
        // Заглушка - в реальности будет gRPC вызов к attendance-service
        return 0L;
    }
}
