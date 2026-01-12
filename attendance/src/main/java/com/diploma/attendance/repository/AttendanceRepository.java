package com.diploma.attendance.repository;

import com.diploma.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    List<Attendance> findByStudentId(UUID studentId);

    List<Attendance> findByStudentIdAndCourseId(UUID studentId, UUID courseId);

    List<Attendance> findByStudentIdAndAttendanceDateBetween(UUID studentId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentId = :studentId AND a.isExcused = false")
    Long countUnexcusedByStudentId(@Param("studentId") UUID studentId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentId = :studentId AND a.courseId = :courseId AND a.isExcused = false")
    Long countUnexcusedByStudentIdAndCourseId(@Param("studentId") UUID studentId, @Param("courseId") UUID courseId);

}
