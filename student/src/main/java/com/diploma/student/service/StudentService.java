package com.diploma.student.service;

import com.diploma.student.exception.StudentGroupNotFoundException;
import com.diploma.student.repository.StudentGroupRepository;
import com.diploma.student.repository.StudentRepository;
import com.diploma.student.dto.request.StudentRequest;
import com.diploma.student.dto.response.StudentResponse;
import com.diploma.student.entity.Student;
import com.diploma.student.exception.StudentNotFoundException;
import com.diploma.student.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StudentService implements BaseService<StudentRequest, StudentResponse> {

    private final StudentRepository repository;
    private final StudentGroupRepository groupRepository;
    private final StudentMapper mapper;

    @Override
    @Transactional
    public StudentResponse create(StudentRequest request) {
        Student entity = mapper.requestToEntity(request);

        if (request.groupId() != null) {
            var group = groupRepository.findById(request.groupId())
                    .orElseThrow(() -> new StudentGroupNotFoundException("StudentGroup not found with ID: " + request.groupId()));
            entity.setGroup(group);
        }

        repository.save(entity);
        return mapper.entityToResponse(entity);
    }

    @Override
    @Transactional
    public StudentResponse update(UUID id, StudentRequest request) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));

        mapper.updateEntityFromRequest(request, student);

        if (request.groupId() != null) {
            var group = groupRepository.findById(request.groupId())
                    .orElseThrow(() -> new StudentGroupNotFoundException("StudentGroup not found with ID: " + request.groupId()));
            student.setGroup(group);
        }

        repository.save(student);
        return mapper.entityToResponse(student);
    }

    @Override
    public StudentResponse getById(UUID id) {
        return repository.findById(id)
                .map(mapper::entityToResponse)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
    }

    @Override
    public List<StudentResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}