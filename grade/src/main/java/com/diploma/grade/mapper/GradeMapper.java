package com.diploma.grade.mapper;

import com.diploma.grade.dto.request.GradeRequest;
import com.diploma.grade.dto.response.GradeResponse;
import com.diploma.grade.entity.Grade;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "studentId", source = "request.studentId")
    @Mapping(target = "courseId", source = "request.courseId")
    @Mapping(target = "gradeValue", source = "request.gradeValue")
    @Mapping(target = "gradeDate", source = "request.gradeDate")
    @Mapping(target = "comment", source = "request.comment")
    Grade requestToEntity(GradeRequest request);

    @Mapping(target = "gradeTypeId", source = "gradeType.id")
    @Mapping(target = "gradeTypeName", source = "gradeType.name")
    GradeResponse entityToResponse(Grade entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "studentId", source = "request.studentId")
    @Mapping(target = "courseId", source = "request.courseId")
    @Mapping(target = "gradeValue", source = "request.gradeValue")
    @Mapping(target = "gradeDate", source = "request.gradeDate")
    @Mapping(target = "comment", source = "request.comment")
    void updateEntityFromRequest(GradeRequest request, @MappingTarget Grade entity);

}
