package com.diploma.student.dto.response;

import java.util.UUID;
import java.time.Instant;

public record StudentResponse(
        UUID id,
        UUID userId,
        String fullName,
        UUID groupId,
        String status,
        Instant createdAt,
        Instant updatedAt
) {}