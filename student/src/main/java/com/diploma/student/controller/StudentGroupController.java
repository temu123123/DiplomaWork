package com.diploma.student.controller;

import com.diploma.student.dto.request.StudentGroupRequest;
import com.diploma.student.dto.response.StudentGroupResponse;
import com.diploma.student.service.StudentGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/student-groups")
@RequiredArgsConstructor
public class StudentGroupController {

    private final StudentGroupService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentGroupResponse getStudentGroup(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentGroupResponse> getAllStudentGroups() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentGroupResponse addStudentGroup(@RequestBody @Valid StudentGroupRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentGroupResponse updateStudentGroup(@PathVariable UUID id, @RequestBody @Valid StudentGroupRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentGroup(@PathVariable UUID id) {
        service.delete(id);
    }
}