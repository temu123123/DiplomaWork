package com.diploma.student.repository;

import com.diploma.student.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, UUID> {
    List<StudentGroup> findBySpecialtyId(UUID specialtyId);
}