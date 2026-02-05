package com.diploma.student.dto.response;

import java.util.UUID;
import java.time.Instant;

public record StudentResponse(
        UUID id,
        String fullName,
        String groupName,
        String email,
        String status,
        Instant createdAt,
        Instant updatedAt
) {}