package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.AcademyAttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyAttendanceRepository extends JpaRepository<AcademyAttendanceEntity, String> {
    List<AcademyAttendanceEntity> findBySessionId(String sessionId);
    List<AcademyAttendanceEntity> findByProgramId(String programId);
    Optional<AcademyAttendanceEntity> findBySessionIdAndUserId(String sessionId, String userId);
}
