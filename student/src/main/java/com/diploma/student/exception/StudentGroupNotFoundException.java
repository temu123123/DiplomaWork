package com.diploma.student.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StudentGroupNotFoundException extends EntityNotFoundException {
    public StudentGroupNotFoundException(String message) {
        super(message);
    }
}
