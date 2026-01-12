package com.diploma.attendance.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record AttendanceRequest(
        @NotNull(message = "Student ID не может быть пустым")
        UUID studentId,

        @NotNull(message = "Course ID не может быть пустым")
        UUID courseId,

        @NotNull(message = "Дата пропуска должна быть указана")
        LocalDate attendanceDate,

        @NotNull(message = "Тип пропуска должен быть указан")
        UUID attendanceTypeId,

        String reason,

        Boolean isExcused
) {}
