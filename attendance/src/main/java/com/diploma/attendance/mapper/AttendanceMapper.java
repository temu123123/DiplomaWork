package com.diploma.attendance.mapper;

import com.diploma.attendance.dto.request.AttendanceRequest;
import com.diploma.attendance.dto.response.AttendanceResponse;
import com.diploma.attendance.entity.Attendance;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "studentId", source = "request.studentId")
    @Mapping(target = "courseId", ignore = true)
    @Mapping(target = "courseName", source = "request.courseName")
    @Mapping(target = "attendanceDate", source = "request.attendanceDate")
    @Mapping(target = "reason", source = "request.reason")
    @Mapping(target = "isExcused", source = "request.isExcused")
    Attendance requestToEntity(AttendanceRequest request);

    @Mapping(target = "attendanceTypeName", source = "attendanceType.name")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AttendanceResponse entityToResponse(Attendance entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "studentId", source = "request.studentId")
    @Mapping(target = "courseId", ignore = true)
    @Mapping(target = "courseName", source = "request.courseName")
    @Mapping(target = "attendanceDate", source = "request.attendanceDate")
    @Mapping(target = "reason", source = "request.reason")
    @Mapping(target = "isExcused", source = "request.isExcused")
    void updateEntityFromRequest(AttendanceRequest request, @MappingTarget Attendance entity);

}
