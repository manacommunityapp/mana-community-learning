package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.StudentSubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSubmissionRepository extends JpaRepository<StudentSubmissionEntity, Long> {
    List<StudentSubmissionEntity> findByAssignmentId(Long assignmentId);
    Optional<StudentSubmissionEntity> findByAssignmentIdAndStudentUserId(Long assignmentId, Long studentUserId);
}