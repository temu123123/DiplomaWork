package com.diploma.student.service;

import com.diploma.student.repository.SpecialtyRepository;
import com.diploma.student.dto.request.SpecialtyRequest;
import com.diploma.student.dto.response.SpecialtyResponse;
import com.diploma.student.entity.Specialty;
import com.diploma.student.exception.SpecialtyNotFoundException;
import com.diploma.student.mapper.SpecialtyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SpecialtyService implements BaseService<SpecialtyRequest, SpecialtyResponse> {

    private final SpecialtyRepository repository;
    private final SpecialtyMapper mapper;

    @Override
    @Transactional
    public SpecialtyResponse create(SpecialtyRequest request) {
        Specialty entity = mapper.requestToEntity(request);
        repository.save(entity);
        return mapper.entityToResponse(entity);
    }

    @Override
    @Transactional
    public SpecialtyResponse update(UUID id, SpecialtyRequest request) {
        Specialty entity = repository.findById(id)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with ID: " + id));
        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);
        return mapper.entityToResponse(entity);
    }

    @Override
    public SpecialtyResponse getById(UUID id) {
        return repository.findById(id)
                .map(mapper::entityToResponse)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with ID: " + id));
    }

    @Override
    public List<SpecialtyResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new SpecialtyNotFoundException("Specialty not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}