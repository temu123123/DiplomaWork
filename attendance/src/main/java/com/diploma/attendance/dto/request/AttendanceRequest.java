package com.diploma.attendance.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

public record AttendanceRequest(
        @NotNull(message = "Student ID не может быть пустым")
        UUID studentId,

        @jakarta.validation.constraints.NotBlank(message = "Название курса должно быть указано")
        @io.swagger.v3.oas.annotations.media.Schema(example = "Высшая математика")
        String courseName,

        @NotNull(message = "Дата пропуска должна быть указана")
        java.time.LocalDate attendanceDate,

        @jakarta.validation.constraints.NotBlank(message = "Тип пропуска должен быть указан")
        @io.swagger.v3.oas.annotations.media.Schema(example = "Болезнь")
        String attendanceTypeName,

        @jakarta.validation.constraints.Size(max = 500, message = "Причина не должна превышать 500 символов")
        String reason,

        Boolean isExcused
) {}
