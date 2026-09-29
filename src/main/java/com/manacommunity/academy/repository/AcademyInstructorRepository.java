package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.AcademyInstructorEntity;
import com.manacommunity.academy.domain.enums.InstructorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyInstructorRepository extends JpaRepository<AcademyInstructorEntity, String> {
    Optional<AcademyInstructorEntity> findByResidentUserId(String residentUserId);
    List<AcademyInstructorEntity> findByCommunityIdAndStatus(String communityId, InstructorStatus status);
    List<AcademyInstructorEntity> findByCommunityId(String communityId);
    List<AcademyInstructorEntity> findByStatus(InstructorStatus status);
}
