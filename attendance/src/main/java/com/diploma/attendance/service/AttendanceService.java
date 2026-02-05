package com.diploma.attendance.service;

import com.diploma.attendance.dto.request.AttendanceRequest;
import com.diploma.attendance.dto.response.AttendanceResponse;
import com.diploma.attendance.entity.Attendance;
import com.diploma.attendance.entity.AttendanceType;
import com.diploma.attendance.exception.AttendanceNotFoundException;
import com.diploma.attendance.exception.AttendanceTypeNotFoundException;
import com.diploma.attendance.mapper.AttendanceMapper;
import com.diploma.attendance.repository.AttendanceRepository;
import com.diploma.attendance.repository.AttendanceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AttendanceService implements BaseService<AttendanceRequest, AttendanceResponse> {

    private final AttendanceRepository repository;
    private final AttendanceTypeRepository attendanceTypeRepository;
    private final AttendanceMapper mapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional
    public AttendanceResponse create(AttendanceRequest request) {
        Attendance entity = mapper.requestToEntity(request);

        AttendanceType attendanceType = attendanceTypeRepository.findByName(request.attendanceTypeName())
                .orElseThrow(() -> new AttendanceTypeNotFoundException("AttendanceType not found with name: " + request.attendanceTypeName()));
        entity.setAttendanceType(attendanceType);

        Attendance saved = repository.save(entity);

        // Отправка события в Kafka
        kafkaTemplate.send("attendance.created", saved.getId().toString(), saved);

        // Проверка на критическое количество пропусков
        Long unexcusedCount = repository.countUnexcusedByStudentId(saved.getStudentId());
        if (unexcusedCount >= 10) {
            kafkaTemplate.send("attendance.critical", saved.getStudentId().toString(), saved);
        }

        return mapper.entityToResponse(saved);
    }

    @Override
    @Transactional
    public AttendanceResponse update(UUID id, AttendanceRequest request) {
        Attendance attendance = repository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found with ID: " + id));

        mapper.updateEntityFromRequest(request, attendance);

        if (request.attendanceTypeName() != null) {
            AttendanceType attendanceType = attendanceTypeRepository.findByName(request.attendanceTypeName())
                    .orElseThrow(() -> new AttendanceTypeNotFoundException("AttendanceType not found with name: " + request.attendanceTypeName()));
            attendance.setAttendanceType(attendanceType);
        }

        Attendance saved = repository.save(attendance);
        return mapper.entityToResponse(saved);
    }

    @Override
    public AttendanceResponse getById(UUID id) {
        return repository.findById(id)
                .map(mapper::entityToResponse)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found with ID: " + id));
    }

    @Override
    public List<AttendanceResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new AttendanceNotFoundException("Attendance not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    public List<AttendanceResponse> getByStudentId(UUID studentId) {
        return repository.findByStudentId(studentId).stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    public Long getUnexcusedCountByStudentId(UUID studentId) {
        return repository.countUnexcusedByStudentId(studentId);
    }
}
