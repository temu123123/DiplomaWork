package com.diploma.student.mapper;

import com.diploma.student.dto.request.StudentGroupRequest;
import com.diploma.student.dto.response.StudentGroupResponse;
import com.diploma.student.entity.StudentGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentGroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specialty", ignore = true)
    StudentGroup requestToEntity(StudentGroupRequest request);

    @Mapping(target = "specialtyId", source = "specialty.id")
    StudentGroupResponse entityToResponse(StudentGroup entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specialty", ignore = true)
    void updateEntityFromRequest(StudentGroupRequest request, @MappingTarget StudentGroup entity);
}
