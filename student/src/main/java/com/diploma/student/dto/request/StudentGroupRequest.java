package com.diploma.student.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class StudentGroupRequest {

    @NotBlank(message = "Название группы не должно быть пустым")
    @Size(max = 50, message = "Название группы слишком длинное")
    private String name;

    @NotNull(message = "Специальность должна быть указана")
    private UUID specialtyId;

    @NotNull(message = "Год обучения должен быть указан")
    @Min(value = 1, message = "Год обучения не может быть меньше 1")
    private Integer year;
}