package com.diploma.student.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record StudentRequest(
        @NotNull(message = "User ID не может быть пустым")
        UUID userId,

        @NotBlank(message = "ФИО студента не может быть пустым")
        @Size(max = 255, message = "ФИО слишком длинное")
        String fullName,

        @NotNull(message = "Группа студента должна быть указана")
        UUID groupId,

        String status
) {}