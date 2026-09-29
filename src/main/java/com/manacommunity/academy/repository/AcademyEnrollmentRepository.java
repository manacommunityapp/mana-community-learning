package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.AcademyEnrollmentEntity;
import com.manacommunity.academy.domain.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyEnrollmentRepository extends JpaRepository<AcademyEnrollmentEntity, String> {
    Optional<AcademyEnrollmentEntity> findByProgramIdAndUserId(String programId, String userId);
    List<AcademyEnrollmentEntity> findByUserId(String userId);
    List<AcademyEnrollmentEntity> findByProgramId(String programId);
    List<AcademyEnrollmentEntity> findByProgramIdAndStatus(String programId, EnrollmentStatus status);
    Optional<AcademyEnrollmentEntity> findByQrPassCode(String qrPassCode);
    boolean existsByProgramIdAndUserIdAndStatus(String programId, String userId, EnrollmentStatus status);
}
