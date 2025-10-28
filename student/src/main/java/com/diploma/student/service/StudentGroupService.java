package com.diploma.student.service;

import com.diploma.student.exception.SpecialtyNotFoundException;
import com.diploma.student.repository.SpecialtyRepository;
import com.diploma.student.repository.StudentGroupRepository;
import com.diploma.student.dto.request.StudentGroupRequest;
import com.diploma.student.dto.response.StudentGroupResponse;
import com.diploma.student.entity.StudentGroup;
import com.diploma.student.exception.StudentGroupNotFoundException;
import com.diploma.student.mapper.StudentGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StudentGroupService implements BaseService<StudentGroupRequest, StudentGroupResponse> {

    private final StudentGroupRepository repository;
    private final SpecialtyRepository specialtyRepository;
    private final StudentGroupMapper mapper;

    @Override
    @Transactional
    public StudentGroupResponse create(StudentGroupRequest request) {
        StudentGroup entity = mapper.requestToEntity(request);

        if (request.specialtyId() != null) {
            var specialty = specialtyRepository.findById(request.specialtyId())
                    .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with ID: " + request.specialtyId()));
            entity.setSpecialty(specialty);
        }

        repository.save(entity);
        return mapper.entityToResponse(entity);
    }

    @Override
    @Transactional
    public StudentGroupResponse update(UUID id, StudentGroupRequest request) {
        StudentGroup group = repository.findById(id)
                .orElseThrow(() -> new StudentGroupNotFoundException("StudentGroup not found with ID: " + id));

        mapper.updateEntityFromRequest(request, group);

        if (request.specialtyId() != null) {
            var specialty = specialtyRepository.findById(request.specialtyId())
                    .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with ID: " + request.specialtyId()));
            group.setSpecialty(specialty);
        }

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
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new StudentGroupNotFoundException("StudentGroup not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}