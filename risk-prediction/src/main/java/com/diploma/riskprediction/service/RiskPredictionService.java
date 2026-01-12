package com.diploma.riskprediction.service;

import com.diploma.riskprediction.dto.response.RiskAssessmentResponse;
import com.diploma.riskprediction.entity.RiskAssessment;
import com.diploma.riskprediction.entity.RiskLevel;
import com.diploma.riskprediction.exception.RiskAssessmentNotFoundException;
import com.diploma.riskprediction.repository.RiskAssessmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RiskPredictionService {

    private final RiskAssessmentRepository repository;
    private final AttendanceDataService attendanceDataService;
    private final GradeDataService gradeDataService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public RiskAssessmentResponse calculateRisk(UUID studentId) {
        log.info("Calculating risk for student: {}", studentId);

        // Получение данных о пропусках и оценках через gRPC
        Long unexcusedCount = attendanceDataService.getUnexcusedCount(studentId);
        Double averageGrade = gradeDataService.getAverageGrade(studentId);

        // Расчет факторов риска
        BigDecimal attendanceFactor = calculateAttendanceFactor(unexcusedCount);
        BigDecimal gradeFactor = calculateGradeFactor(averageGrade);

        // Расчет общего риска (взвешенная сумма)
        BigDecimal riskScore = attendanceFactor.multiply(new BigDecimal("0.6"))
                .add(gradeFactor.multiply(new BigDecimal("0.4")));

        RiskLevel riskLevel = determineRiskLevel(riskScore);

        RiskAssessment assessment = new RiskAssessment();
        assessment.setStudentId(studentId);
        assessment.setRiskLevel(riskLevel);
        assessment.setRiskScore(riskScore);
        assessment.setAttendanceFactor(attendanceFactor);
        assessment.setGradeFactor(gradeFactor);
        assessment.setCalculatedAt(LocalDateTime.now());
        assessment.setComment(generateComment(riskLevel, unexcusedCount, averageGrade));

        RiskAssessment saved = repository.save(assessment);

        // Отправка события в Kafka
        kafkaTemplate.send("risk.updated", studentId.toString(), saved);

        return mapToResponse(saved);
    }

    public RiskAssessmentResponse getLatestRisk(UUID studentId) {
        return repository.findFirstByStudentIdOrderByCalculatedAtDesc(studentId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RiskAssessmentNotFoundException("Risk assessment not found for student: " + studentId));
    }

    public List<RiskAssessmentResponse> getRiskHistory(UUID studentId) {
        return repository.findByStudentIdOrderByCalculatedAtDesc(studentId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Scheduled(cron = "0 0 2 * * ?") // Каждый день в 2:00
    @Transactional
    public void recalculateAllRisks() {
        log.info("Starting scheduled risk recalculation");
        // TODO: Получить список всех студентов и пересчитать риски
        // Это можно сделать через вызов student-service через gRPC
    }

    private BigDecimal calculateAttendanceFactor(Long unexcusedCount) {
        if (unexcusedCount == null || unexcusedCount == 0) {
            return BigDecimal.ZERO;
        }
        // Нормализация: 0 пропусков = 0, 20+ пропусков = 100
        BigDecimal factor = new BigDecimal(unexcusedCount).multiply(new BigDecimal("5"));
        return factor.min(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateGradeFactor(Double averageGrade) {
        if (averageGrade == null) {
            return new BigDecimal("50"); // Средний риск если нет оценок
        }
        // Инверсия: высокая оценка = низкий риск, низкая оценка = высокий риск
        BigDecimal factor = new BigDecimal("100").subtract(new BigDecimal(averageGrade.toString()));
        return factor.max(BigDecimal.ZERO).min(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
    }

    private RiskLevel determineRiskLevel(BigDecimal riskScore) {
        if (riskScore.compareTo(new BigDecimal("75")) >= 0) {
            return RiskLevel.CRITICAL;
        } else if (riskScore.compareTo(new BigDecimal("50")) >= 0) {
            return RiskLevel.HIGH;
        } else if (riskScore.compareTo(new BigDecimal("25")) >= 0) {
            return RiskLevel.MEDIUM;
        } else {
            return RiskLevel.LOW;
        }
    }

    private String generateComment(RiskLevel riskLevel, Long unexcusedCount, Double averageGrade) {
        return String.format("Risk Level: %s. Unexcused absences: %d. Average grade: %.2f",
                riskLevel, unexcusedCount != null ? unexcusedCount : 0, averageGrade != null ? averageGrade : 0.0);
    }

    private RiskAssessmentResponse mapToResponse(RiskAssessment assessment) {
        return new RiskAssessmentResponse(
                assessment.getId(),
                assessment.getStudentId(),
                assessment.getRiskLevel(),
                assessment.getRiskScore(),
                assessment.getAttendanceFactor(),
                assessment.getGradeFactor(),
                assessment.getCalculatedAt(),
                assessment.getComment()
        );
    }
}
