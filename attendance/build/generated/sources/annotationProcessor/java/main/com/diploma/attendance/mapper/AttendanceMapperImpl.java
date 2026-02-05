package com.diploma.attendance.mapper;

import com.diploma.attendance.dto.request.AttendanceRequest;
import com.diploma.attendance.dto.response.AttendanceResponse;
import com.diploma.attendance.entity.Attendance;
import com.diploma.attendance.entity.AttendanceType;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-05T10:45:24+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class AttendanceMapperImpl implements AttendanceMapper {

    @Override
    public Attendance requestToEntity(AttendanceRequest request) {
        if ( request == null ) {
            return null;
        }

        Attendance attendance = new Attendance();

        attendance.setStudentId( request.studentId() );
        attendance.setCourseName( request.courseName() );
        attendance.setAttendanceDate( request.attendanceDate() );
        attendance.setReason( request.reason() );
        attendance.setIsExcused( request.isExcused() );

        return attendance;
    }

    @Override
    public AttendanceResponse entityToResponse(Attendance entity) {
        if ( entity == null ) {
            return null;
        }

        String attendanceTypeName = null;
        UUID id = null;
        UUID studentId = null;
        String courseName = null;
        LocalDate attendanceDate = null;
        String reason = null;
        Boolean isExcused = null;

        attendanceTypeName = entityAttendanceTypeName( entity );
        id = entity.getId();
        studentId = entity.getStudentId();
        courseName = entity.getCourseName();
        attendanceDate = entity.getAttendanceDate();
        reason = entity.getReason();
        isExcused = entity.getIsExcused();

        Instant createdAt = null;
        Instant updatedAt = null;

        AttendanceResponse attendanceResponse = new AttendanceResponse( id, studentId, courseName, attendanceDate, attendanceTypeName, reason, isExcused, createdAt, updatedAt );

        return attendanceResponse;
    }

    @Override
    public void updateEntityFromRequest(AttendanceRequest request, Attendance entity) {
        if ( request == null ) {
            return;
        }

        entity.setStudentId( request.studentId() );
        entity.setCourseName( request.courseName() );
        entity.setAttendanceDate( request.attendanceDate() );
        entity.setReason( request.reason() );
        entity.setIsExcused( request.isExcused() );
    }

    private String entityAttendanceTypeName(Attendance attendance) {
        AttendanceType attendanceType = attendance.getAttendanceType();
        if ( attendanceType == null ) {
            return null;
        }
        return attendanceType.getName();
    }
}
