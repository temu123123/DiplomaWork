package com.diploma.student.service;

import com.diploma.student.dao.CourseRepository;
import com.diploma.student.dto.request.CourseRequest;
import com.diploma.student.dto.response.CourseResponse;
import com.diploma.student.entity.Course;
import com.diploma.student.exception.CourseNotFoundException;
import com.diploma.student.mapper.CourseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseService implements BaseService<CourseRequest, CourseResponse> {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseResponse create(CourseRequest request) {
        Course entity = courseMapper.requestToEntity(request);
        courseRepository.save(entity);
        return courseMapper.entityToResponse(entity);
    }

    @Override
    public CourseResponse update(UUID id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));
        courseMapper.updateEntityFromRequest(request, course);
        courseRepository.save(course);
        return courseMapper.entityToResponse(course);
    }

    @Override
    public CourseResponse getById(UUID id) {
        return courseRepository.findById(id)
                .map(courseMapper::entityToResponse)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));
    }

    @Override
    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::entityToResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException("Course not found with ID: " + id);
        }
        courseRepository.deleteById(id);
    }
}
