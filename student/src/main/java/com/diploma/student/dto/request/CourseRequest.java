package com.diploma.student.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class CourseRequest {

    @NotBlank(message = "Название специальности не должно быть пустым")
    private String name;

    private Set<UUID> specialtyIds;

    @NotNull(message = "Семестр не может быть пустым")
    @Min(value = 1, message = "Курс должен быть не меньше 1")
    @Max(value = 6, message = "Курс не может быть больше 6")
    private Integer semester;

    @NotNull(message = "Необходимо указать учителя")
    private UUID teacherId;

}
