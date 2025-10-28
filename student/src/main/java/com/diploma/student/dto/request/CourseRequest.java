package com.diploma.student.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record CourseRequest(
        @NotBlank(message = "Название специальности не должно быть пустым")
        String name,

        Set<UUID> specialtyIds,

        @NotNull(message = "Семестр не может быть пустым")
        @Min(value = 1, message = "Курс должен быть не меньше 1")
        @Max(value = 6, message = "Курс не может быть больше 6")
        Integer semester,

        @NotNull(message = "Необходимо указать учителя")
        UUID teacherId
) {}
