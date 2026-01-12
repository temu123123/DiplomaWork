package com.diploma.attendance.advice;

import com.diploma.attendance.exception.AttendanceNotFoundException;
import com.diploma.attendance.exception.AttendanceTypeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AttendanceAdvice {

    @ExceptionHandler(AttendanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleAttendanceNotFound(AttendanceNotFoundException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(AttendanceTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleAttendanceTypeNotFound(AttendanceTypeNotFoundException e) {
        return Map.of("error", e.getMessage());
    }
}
