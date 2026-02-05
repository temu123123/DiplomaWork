package com.diploma.student.controller;

import com.diploma.student.dto.request.AssignGroupRequest;
import com.diploma.student.dto.request.StudentRequest;
import com.diploma.student.dto.response.StudentResponse;
import com.diploma.student.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Student", description = "Student Management API")
public class StudentController {

    private final StudentService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'DEAN')")
    public StudentResponse getStudent(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'DEAN')")
    public List<StudentResponse> getAllStudents() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public StudentResponse addStudent(@RequestBody @Valid StudentRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public StudentResponse updateStudent(@PathVariable UUID id, @RequestBody @Valid StudentRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/assign-group")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @io.swagger.v3.oas.annotations.Operation(summary = "Assign a group to a student", description = "Admin only. Updates the student's group and sets status to ACTIVE.")
    public StudentResponse assignGroup(@RequestBody @Valid AssignGroupRequest request) {
        return service.assignGroup(request);
    }
}