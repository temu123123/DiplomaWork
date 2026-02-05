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
        @io.swagger.v3.oas.annotations.media.Schema(example = "Иванов Иван Иванович")
        String fullName,

        @NotBlank(message = "Группа студента должна быть указана")
        @io.swagger.v3.oas.annotations.media.Schema(example = "ИВТ-21-1")
        String groupName,

        @jakarta.validation.constraints.Email(message = "Некорректный формат email")
        @io.swagger.v3.oas.annotations.media.Schema(example = "ivanov@university.com")
        String email,

        @io.swagger.v3.oas.annotations.media.Schema(example = "ACTIVE")
        String status
) {}