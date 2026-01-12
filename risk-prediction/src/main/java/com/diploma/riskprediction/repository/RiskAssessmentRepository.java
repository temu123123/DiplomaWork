package com.diploma.riskprediction.repository;

import com.diploma.riskprediction.entity.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, UUID> {

    Optional<RiskAssessment> findFirstByStudentIdOrderByCalculatedAtDesc(UUID studentId);

    List<RiskAssessment> findByStudentIdOrderByCalculatedAtDesc(UUID studentId);

}
