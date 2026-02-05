package com.diploma.grade.service;

import com.diploma.grade.dto.request.GradeRequest;
import com.diploma.grade.dto.response.GradeResponse;
import com.diploma.grade.entity.Grade;
import com.diploma.grade.entity.GradeType;
import com.diploma.grade.exception.GradeNotFoundException;
import com.diploma.grade.exception.GradeTypeNotFoundException;
import com.diploma.grade.mapper.GradeMapper;
import com.diploma.grade.repository.GradeRepository;
import com.diploma.grade.repository.GradeTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GradeService implements BaseService<GradeRequest, GradeResponse> {

    private final GradeRepository repository;
    private final GradeTypeRepository gradeTypeRepository;
    private final GradeMapper mapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final BigDecimal LOW_GRADE_THRESHOLD = new BigDecimal("60.0");

    @Override
    @Transactional
    public GradeResponse create(GradeRequest request) {
        Grade entity = mapper.requestToEntity(request);

        GradeType gradeType = gradeTypeRepository.findByName(request.gradeTypeName())
                .orElseThrow(() -> new GradeTypeNotFoundException("GradeType not found with name: " + request.gradeTypeName()));
        entity.setGradeType(gradeType);

        if (request.gradeDate() == null) {
            entity.setGradeDate(java.time.LocalDate.now());
        }

        Grade saved = repository.save(entity);

        // Отправка события в Kafka
        kafkaTemplate.send("grade.created", saved.getId().toString(), saved);

        // Проверка на низкую оценку
        if (saved.getGradeValue().compareTo(LOW_GRADE_THRESHOLD) < 0) {
            kafkaTemplate.send("grade.low", saved.getStudentId().toString(), saved);
        }

        return mapper.entityToResponse(saved);
    }

    @Override
    @Transactional
    public GradeResponse update(UUID id, GradeRequest request) {
        Grade grade = repository.findById(id)
                .orElseThrow(() -> new GradeNotFoundException("Grade not found with ID: " + id));

        mapper.updateEntityFromRequest(request, grade);

        if (request.gradeTypeName() != null) {
            GradeType gradeType = gradeTypeRepository.findByName(request.gradeTypeName())
                    .orElseThrow(() -> new GradeTypeNotFoundException("GradeType not found with name: " + request.gradeTypeName()));
            grade.setGradeType(gradeType);
        }

        Grade saved = repository.save(grade);
        return mapper.entityToResponse(saved);
    }

    @Override
    public GradeResponse getById(UUID id) {
        return repository.findById(id)
                .map(mapper::entityToResponse)
                .orElseThrow(() -> new GradeNotFoundException("Grade not found with ID: " + id));
    }

    @Override
    public List<GradeResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new GradeNotFoundException("Grade not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    public List<GradeResponse> getByStudentId(UUID studentId) {
        return repository.findByStudentId(studentId).stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    public Double getAverageGradeByStudentId(UUID studentId) {
        return repository.calculateAverageGradeByStudentId(studentId);
    }
}
