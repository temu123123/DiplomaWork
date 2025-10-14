package com.diploma.student.dto.response;

import java.util.UUID;

public record SpecialtyResponse(
        UUID id,
        String name,
        String description
) {}