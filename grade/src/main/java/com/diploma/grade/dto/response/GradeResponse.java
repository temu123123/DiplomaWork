package com.diploma.grade.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record GradeResponse(
        UUID id,
        UUID studentId,
        String courseName,
        String gradeTypeName,
        BigDecimal gradeValue,
        LocalDate gradeDate,
        String comment,
        Instant createdAt,
        Instant updatedAt
) {}
