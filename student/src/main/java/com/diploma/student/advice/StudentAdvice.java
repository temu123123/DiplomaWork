package com.diploma.student.advice;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class StudentAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ProblemDetail> handleEntityNotFoundException(
            EntityNotFoundException ex,
            WebRequest webRequest) {
        log.error(ex.getMessage(), ex);
        ProblemDetail problemDetail = createProblemDetail(ex, HttpStatus.NOT_FOUND, ex.getMessage(), null, null, webRequest);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

}