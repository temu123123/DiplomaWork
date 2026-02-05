package com.diploma.student.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record CourseRequest(
        @NotBlank(message = "Название курса не должно быть пустым")
        @io.swagger.v3.oas.annotations.media.Schema(example = "Высшая математика")
        String name,

        @io.swagger.v3.oas.annotations.media.Schema(example = "[\"Информатика и вычислительная техника\"]")
        java.util.Set<String> specialtyNames,

        @NotNull(message = "Семестр не может быть пустым")
        @Min(value = 1, message = "Семестр должен быть не меньше 1")
        @Max(value = 12, message = "Семестр не может быть больше 12")
        @io.swagger.v3.oas.annotations.media.Schema(example = "1")
        Integer semester,

        @NotNull(message = "Необходимо указать ID преподавателя")
        UUID teacherId
) {}
