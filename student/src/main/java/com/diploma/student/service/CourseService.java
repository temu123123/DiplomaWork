package com.diploma.student.service;

import com.diploma.student.repository.CourseRepository;
import com.diploma.student.dto.request.CourseRequest;
import com.diploma.student.dto.response.CourseResponse;
import com.diploma.student.entity.Course;
import com.diploma.student.exception.CourseNotFoundException;
import com.diploma.student.mapper.CourseMapper;
import com.diploma.student.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CourseService implements BaseService<CourseRequest, CourseResponse> {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final SpecialtyRepository specialtyRepository;

    @Override
    @Transactional
    public CourseResponse create(CourseRequest request) {
        Course entity = courseMapper.requestToEntity(request);

        if (request.specialtyNames() != null && !request.specialtyNames().isEmpty()) {
            Set<com.diploma.student.entity.Specialty> specialties = request.specialtyNames().stream()
                    .map(name -> specialtyRepository.findByName(name)
                            .orElseThrow(() -> new com.diploma.student.exception.SpecialtyNotFoundException("Specialty not found with name: " + name)))
                    .collect(java.util.stream.Collectors.toSet());
            entity.setSpecialties(specialties);
        }

        courseRepository.save(entity);
        return courseMapper.entityToResponse(entity);
    }

    @Override
    @Transactional
    public CourseResponse update(UUID id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));

        courseMapper.updateEntityFromRequest(request, course);

        if (request.specialtyNames() != null) {
            Set<com.diploma.student.entity.Specialty> specialties = request.specialtyNames().stream()
                    .map(name -> specialtyRepository.findByName(name)
                            .orElseThrow(() -> new com.diploma.student.exception.SpecialtyNotFoundException("Specialty not found with name: " + name)))
                    .collect(java.util.stream.Collectors.toSet());
            course.setSpecialties(specialties);
        }

        return courseMapper.entityToResponse(courseRepository.save(course));
    }

    @Override
    public CourseResponse getById(UUID id) {
        return courseRepository.findById(id)
                .map(courseMapper::entityToResponse)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
    }

    @Override
    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
}