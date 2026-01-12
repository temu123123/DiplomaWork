package com.diploma.riskprediction.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "risk_assessment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskAssessment {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false)
    private RiskLevel riskLevel;

    @NotNull
    @Column(name = "risk_score", precision = 5, scale = 2, nullable = false)
    private BigDecimal riskScore;

    @Column(name = "attendance_factor", precision = 5, scale = 2)
    private BigDecimal attendanceFactor;

    @Column(name = "grade_factor", precision = 5, scale = 2)
    private BigDecimal gradeFactor;

    @Column(name = "calculated_at", nullable = false)
    private LocalDateTime calculatedAt;

    @Column(name = "comment", length = 1000)
    private String comment;

}
