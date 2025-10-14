package com.diploma.student.service;

import com.diploma.student.dao.StudentGroupRepository;
import com.diploma.student.dto.request.StudentGroupRequest;
import com.diploma.student.dto.response.StudentGroupResponse;
import com.diploma.student.entity.StudentGroup;
import com.diploma.student.exception.StudentGroupNotFoundException;
import com.diploma.student.mapper.StudentGroupMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class StudentGroupService implements BaseService<StudentGroupRequest, StudentGroupResponse> {

    private final StudentGroupRepository repository;
    private final StudentGroupMapper mapper;

    @Override
    public StudentGroupResponse create(StudentGroupRequest request) {
        StudentGroup entity = mapper.requestToEntity(request);
        repository.save(entity);
        return mapper.entityToResponse(entity);
    }

    @Override
    public StudentGroupResponse update(UUID id, StudentGroupRequest request) {
        StudentGroup group = repository.findById(id)
                .orElseThrow(() -> new StudentGroupNotFoundException("StudentGroup not found with ID: " + id));
        mapper.updateEntityFromRequest(request, group);
        repository.save(group);
        return mapper.entityToResponse(group);
    }

    @Override
    public StudentGroupResponse getById(UUID id) {
        return repository.findById(id)
                .map(mapper::entityToResponse)
                .orElseThrow(() -> new StudentGroupNotFoundException("StudentGroup not found with ID: " + id));
    }

    @Override
    public List<StudentGroupResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new StudentGroupNotFoundException("StudentGroup not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}