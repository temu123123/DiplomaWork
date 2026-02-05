package com.diploma.grade.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "grade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grade extends AuditEntity {

    @NotNull
    @Column(name = "student_id")
    private UUID studentId;

    @Column(name = "course_id")
    private UUID courseId;

    @Column(name = "course_name")
    private String courseName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "grade_type_id")
    private GradeType gradeType;

    @NotNull
    @Column(name = "grade_value", precision = 5, scale = 2)
    private BigDecimal gradeValue;

    @Column(name = "grade_date")
    private LocalDate gradeDate;

    @Column(name = "comment", length = 1000)
    private String comment;

}
