package com.diploma.student.dto.response;

import java.util.Set;
import java.util.UUID;

public record CourseResponse(
        UUID id,
        String name,
        Set<String> specialtyNames,
        Integer semester,
        UUID teacherId
) {}
