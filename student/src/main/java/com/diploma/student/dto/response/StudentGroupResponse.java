package com.diploma.student.dto.response;


import java.util.UUID;

public record StudentGroupResponse(
        UUID id,
        String name,
        String specialtyName,
        Integer year
) {}