package com.diploma.student.service;

import com.diploma.student.dao.SpecialtyRepository;
import com.diploma.student.dto.request.SpecialtyRequest;
import com.diploma.student.dto.response.SpecialtyResponse;
import com.diploma.student.entity.Specialty;
import com.diploma.student.exception.SpecialtyNotFoundException;
import com.diploma.student.mapper.SpecialtyMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class SpecialtyService implements BaseService<SpecialtyRequest, SpecialtyResponse> {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    @Override
    public SpecialtyResponse create(SpecialtyRequest request) {
        Specialty entity = specialtyMapper.requestToEntity(request);
        specialtyRepository.save(entity);
        return specialtyMapper.entityToResponse(entity);
    }

    @Override
    public SpecialtyResponse update(UUID id, SpecialtyRequest request) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with ID: " + id));
        specialtyMapper.updateEntityFromRequest(request, specialty);
        specialtyRepository.save(specialty);
        return specialtyMapper.entityToResponse(specialty);
    }

    @Override
    public SpecialtyResponse getById(UUID id) {
        return specialtyRepository.findById(id)
                .map(specialtyMapper::entityToResponse)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with ID: " + id));
    }

    @Override
    public List<SpecialtyResponse> getAll() {
        return specialtyRepository.findAll().stream()
                .map(specialtyMapper::entityToResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!specialtyRepository.existsById(id)) {
            throw new SpecialtyNotFoundException("Specialty not found with ID: " + id);
        }
        specialtyRepository.deleteById(id);
    }
}