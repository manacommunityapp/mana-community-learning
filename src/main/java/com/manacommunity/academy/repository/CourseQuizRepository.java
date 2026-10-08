package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.CourseQuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseQuizRepository extends JpaRepository<CourseQuizEntity, Long> {
    List<CourseQuizEntity> findByModuleId(Long moduleId);
}