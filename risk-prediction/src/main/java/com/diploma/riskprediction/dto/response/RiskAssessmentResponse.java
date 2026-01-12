package com.diploma.riskprediction.dto.response;

import com.diploma.riskprediction.entity.RiskLevel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RiskAssessmentResponse(
        UUID id,
        UUID studentId,
        RiskLevel riskLevel,
        BigDecimal riskScore,
        BigDecimal attendanceFactor,
        BigDecimal gradeFactor,
        LocalDateTime calculatedAt,
        String comment
) {}
