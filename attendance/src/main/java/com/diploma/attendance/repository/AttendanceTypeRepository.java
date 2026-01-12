package com.diploma.attendance.repository;

import com.diploma.attendance.entity.AttendanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceTypeRepository extends JpaRepository<AttendanceType, UUID> {

    Optional<AttendanceType> findByName(String name);

}
