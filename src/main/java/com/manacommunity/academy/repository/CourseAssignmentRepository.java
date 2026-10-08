package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.CourseAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseAssignmentRepository extends JpaRepository<CourseAssignmentEntity, Long> {
    List<CourseAssignmentEntity> findByModuleId(Long moduleId);
}