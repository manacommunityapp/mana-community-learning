package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.AcademyReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyReviewRepository extends JpaRepository<AcademyReviewEntity, String> {
    List<AcademyReviewEntity> findByProgramId(String programId);
    List<AcademyReviewEntity> findByInstructorId(String instructorId);
    Optional<AcademyReviewEntity> findByProgramIdAndUserId(String programId, String userId);
}
