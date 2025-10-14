package com.diploma.student.mapper;

import com.diploma.student.dto.request.CourseRequest;
import com.diploma.student.dto.response.CourseResponse;
import com.diploma.student.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specialties", ignore = true)
    Course requestToEntity(CourseRequest request);

    CourseResponse entityToResponse(Course entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specialties", ignore = true)
    void updateEntityFromRequest(CourseRequest request, @MappingTarget Course entity);
}
