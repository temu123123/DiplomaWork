package com.diploma.grade.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record GradeRequest(
        @NotNull(message = "Student ID не может быть пустым")
        UUID studentId,

        @NotNull(message = "Course ID не может быть пустым")
        UUID courseId,

        @NotNull(message = "Тип оценки должен быть указан")
        UUID gradeTypeId,

        @NotNull(message = "Оценка должна быть указана")
        @DecimalMin(value = "0.0", message = "Оценка не может быть отрицательной")
        @DecimalMax(value = "100.0", message = "Оценка не может быть больше 100")
        BigDecimal gradeValue,

        LocalDate gradeDate,

        String comment
) {}
