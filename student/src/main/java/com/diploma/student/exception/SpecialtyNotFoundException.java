package com.diploma.student.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SpecialtyNotFoundException extends EntityNotFoundException {
    public SpecialtyNotFoundException(String message) {
        super(message);
    }
}
