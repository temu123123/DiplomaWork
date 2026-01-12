package com.diploma.attendance.dto.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record AttendanceResponse(
        UUID id,
        UUID studentId,
        UUID courseId,
        LocalDate attendanceDate,
        UUID attendanceTypeId,
        String attendanceTypeName,
        String reason,
        Boolean isExcused,
        Instant createdAt,
        Instant updatedAt
) {}
