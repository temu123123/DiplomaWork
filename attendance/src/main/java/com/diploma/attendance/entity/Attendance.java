package com.diploma.attendance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance extends AuditEntity {

    @NotNull
    @Column(name = "student_id")
    private UUID studentId;

    @Column(name = "course_id")
    private UUID courseId;

    @Column(name = "course_name")
    private String courseName;

    @NotNull
    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "attendance_type_id")
    private AttendanceType attendanceType;

    @Column(name = "reason", length = 500)
    private String reason;

    @Column(name = "is_excused")
    private Boolean isExcused;

}
