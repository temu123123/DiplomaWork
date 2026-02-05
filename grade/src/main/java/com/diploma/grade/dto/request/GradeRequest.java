package com.diploma.grade.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record GradeRequest(
        @NotNull(message = "Student ID не может быть пустым")
        UUID studentId,

        @jakarta.validation.constraints.NotBlank(message = "Название курса должно быть указано")
        @io.swagger.v3.oas.annotations.media.Schema(example = "Высшая математика")
        String courseName,

        @jakarta.validation.constraints.NotBlank(message = "Тип оценки должен быть указан")
        @io.swagger.v3.oas.annotations.media.Schema(example = "Экзамен")
        String gradeTypeName,

        @NotNull(message = "Оценка должна быть указана")
        @DecimalMin(value = "0.0", message = "Оценка не может быть отрицательной")
        @DecimalMax(value = "100.0", message = "Оценка не может быть больше 100")
        BigDecimal gradeValue,

        LocalDate gradeDate,

        @Size(max = 1000, message = "Комментарий не должен превышать 1000 символов")
        String comment
) {}
