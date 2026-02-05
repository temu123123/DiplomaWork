package com.diploma.student.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record StudentGroupRequest(
        @NotBlank(message = "Название группы не должно быть пустым")
        @Size(min = 5, max = 50, message = "Название группы должно быть от 5 до 50 символов")
        @io.swagger.v3.oas.annotations.media.Schema(example = "ИВТ-21-1")
        String name,

        @NotBlank(message = "Специальность должна быть указана")
        @io.swagger.v3.oas.annotations.media.Schema(example = "Информатика и вычислительная техника")
        String specialtyName,

        @NotNull(message = "Год обучения должен быть указан")
        @Min(value = 1, message = "Год обучения не может быть меньше 1")
        @io.swagger.v3.oas.annotations.media.Schema(example = "1", description = "Курс обучения (1-6)")
        @jakarta.validation.constraints.Max(value = 6, message = "Год обучения не может быть больше 6")
        Integer year
) {}