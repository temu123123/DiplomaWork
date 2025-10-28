package com.diploma.student.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SpecialtyRequest(
        @NotBlank(message = "Название специальности не должно быть пустым")
        @Size(max = 255, message = "Название специальности не должно превышать 255 символов")
        String name,

        @Size(max = 1000, message = "Описание специальности слишком длинное")
        String description
) {}