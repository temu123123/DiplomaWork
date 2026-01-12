package com.diploma.attendance.exception;

public class AttendanceTypeNotFoundException extends RuntimeException {
    public AttendanceTypeNotFoundException(String message) {
        super(message);
    }
}
