package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.CourseModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseModuleRepository extends JpaRepository<CourseModuleEntity, Long> {
    List<CourseModuleEntity> findByProgramIdOrderBySequenceOrderAsc(Long programId);
}