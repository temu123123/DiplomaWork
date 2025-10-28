package com.diploma.student.mapper;

import com.diploma.student.dto.request.StudentRequest;
import com.diploma.student.dto.response.StudentResponse;
import com.diploma.student.entity.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "userId", source = "request.userId")
    @Mapping(target = "fullName", source = "request.fullName")
    @Mapping(target = "status", source = "request.status")
    Student requestToEntity(StudentRequest request);

    @Mapping(target = "groupId", source = "group.id")
    StudentResponse entityToResponse(Student entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "userId", source = "request.userId")
    @Mapping(target = "fullName", source = "request.fullName")
    @Mapping(target = "status", source = "request.status")
    void updateEntityFromRequest(StudentRequest request, @MappingTarget Student entity);
}
