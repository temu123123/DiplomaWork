package com.diploma.student.mapper;

import com.diploma.student.dto.request.CourseRequest;
import com.diploma.student.dto.response.CourseResponse;
import com.diploma.student.entity.BaseEntity;
import com.diploma.student.entity.Course;
import com.diploma.student.entity.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specialties", ignore = true)
    Course requestToEntity(CourseRequest request);

    @Mapping(target = "specialtyIds", source = "specialties")
    CourseResponse entityToResponse(Course entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specialties", ignore = true)
    void updateEntityFromRequest(CourseRequest request, @MappingTarget Course entity);

    default Set<UUID> mapSpecialtiesToIds(Set<Specialty> specialties) {
        return specialties == null
                ? Set.of()
                : specialties.stream()
                .map(BaseEntity::getId)
                .collect(java.util.stream.Collectors.toSet());
    }
}
