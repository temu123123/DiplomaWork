package com.diploma.grade.repository;

import com.diploma.grade.entity.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GradeTypeRepository extends JpaRepository<GradeType, UUID> {

    Optional<GradeType> findByName(String name);

}
