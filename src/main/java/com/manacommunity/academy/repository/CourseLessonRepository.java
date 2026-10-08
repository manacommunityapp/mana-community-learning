package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.CourseLessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseLessonRepository extends JpaRepository<CourseLessonEntity, Long> {
    List<CourseLessonEntity> findByModuleIdOrderBySequenceOrderAsc(Long moduleId);
}