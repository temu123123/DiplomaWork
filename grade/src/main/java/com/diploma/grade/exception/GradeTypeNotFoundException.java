package com.diploma.grade.exception;

public class GradeTypeNotFoundException extends RuntimeException {
    public GradeTypeNotFoundException(String message) {
        super(message);
    }
}
