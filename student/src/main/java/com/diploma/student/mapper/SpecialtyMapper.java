package com.diploma.student.mapper;

import com.diploma.student.dto.request.SpecialtyRequest;
import com.diploma.student.dto.response.SpecialtyResponse;
import com.diploma.student.entity.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Specialty requestToEntity(SpecialtyRequest request);

    SpecialtyResponse entityToResponse(Specialty entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    void updateEntityFromRequest(SpecialtyRequest request, @MappingTarget Specialty entity);
}
