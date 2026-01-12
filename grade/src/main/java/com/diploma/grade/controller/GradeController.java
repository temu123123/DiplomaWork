package com.diploma.grade.controller;

import com.diploma.grade.dto.request.GradeRequest;
import com.diploma.grade.dto.response.GradeResponse;
import com.diploma.grade.service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GradeResponse getGrade(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GradeResponse> getAllGrades() {
        return service.getAll();
    }

    @GetMapping("/student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<GradeResponse> getGradesByStudent(@PathVariable UUID studentId) {
        return service.getByStudentId(studentId);
    }

    @GetMapping("/student/{studentId}/average")
    @ResponseStatus(HttpStatus.OK)
    public Double getAverageGradeByStudent(@PathVariable UUID studentId) {
        return service.getAverageGradeByStudentId(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GradeResponse addGrade(@RequestBody @Valid GradeRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GradeResponse updateGrade(@PathVariable UUID id, @RequestBody @Valid GradeRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGrade(@PathVariable UUID id) {
        service.delete(id);
    }
}
