package com.diploma.attendance.controller;

import com.diploma.attendance.dto.request.AttendanceRequest;
import com.diploma.attendance.dto.response.AttendanceResponse;
import com.diploma.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AttendanceResponse getAttendance(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AttendanceResponse> getAllAttendances() {
        return service.getAll();
    }

    @GetMapping("/student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AttendanceResponse> getAttendancesByStudent(@PathVariable UUID studentId) {
        return service.getByStudentId(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceResponse addAttendance(@RequestBody @Valid AttendanceRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AttendanceResponse updateAttendance(@PathVariable UUID id, @RequestBody @Valid AttendanceRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttendance(@PathVariable UUID id) {
        service.delete(id);
    }
}
