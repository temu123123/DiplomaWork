package com.diploma.grade.repository;

import com.diploma.grade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GradeRepository extends JpaRepository<Grade, UUID> {

    List<Grade> findByStudentId(UUID studentId);

    List<Grade> findByStudentIdAndCourseId(UUID studentId, UUID courseId);

    @Query("SELECT AVG(g.gradeValue) FROM Grade g WHERE g.studentId = :studentId")
    Double calculateAverageGradeByStudentId(@Param("studentId") UUID studentId);

    @Query("SELECT AVG(g.gradeValue) FROM Grade g WHERE g.studentId = :studentId AND g.courseId = :courseId")
    Double calculateAverageGradeByStudentIdAndCourseId(@Param("studentId") UUID studentId, @Param("courseId") UUID courseId);

    @Query("SELECT COUNT(g) FROM Grade g WHERE g.studentId = :studentId AND g.gradeValue < :threshold")
    Long countLowGradesByStudentId(@Param("studentId") UUID studentId, @Param("threshold") java.math.BigDecimal threshold);

}
