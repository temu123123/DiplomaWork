package com.diploma.grade.advice;

import com.diploma.grade.exception.GradeNotFoundException;
import com.diploma.grade.exception.GradeTypeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GradeAdvice {

    @ExceptionHandler(GradeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleGradeNotFound(GradeNotFoundException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(GradeTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleGradeTypeNotFound(GradeTypeNotFoundException e) {
        return Map.of("error", e.getMessage());
    }
}
