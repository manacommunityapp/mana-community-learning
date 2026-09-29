package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.AcademyWaitlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyWaitlistRepository extends JpaRepository<AcademyWaitlistEntity, String> {
    List<AcademyWaitlistEntity> findByProgramIdOrderByPositionAsc(String programId);
    Optional<AcademyWaitlistEntity> findByProgramIdAndUserId(String programId, String userId);
    Optional<AcademyWaitlistEntity> findFirstByProgramIdAndClaimedFalseOrderByPositionAsc(String programId);
    int countByProgramId(String programId);
}
