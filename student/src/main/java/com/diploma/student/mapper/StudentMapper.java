package com.diploma.student.mapper;

import com.diploma.student.dto.request.StudentRequest;
import com.diploma.student.dto.response.StudentResponse;
import com.diploma.student.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = java.time.Instant.class)
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "group", ignore = true) // если нужно маппить вручную
    Student requestToEntity(StudentRequest request);

    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt().toInstant(java.time.ZoneOffset.UTC))")
    @Mapping(target = "updatedAt", expression = "java(entity.getUpdatedAt().toInstant(java.time.ZoneOffset.UTC))")
    StudentResponse entityToResponse(Student entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "group", ignore = true)
    void updateEntityFromRequest(StudentRequest request, @MappingTarget Student entity);
}
