package com.diploma.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StudentGroupNotFoundException extends RuntimeException {
    public StudentGroupNotFoundException(String message) {
        super(message);
    }
}
