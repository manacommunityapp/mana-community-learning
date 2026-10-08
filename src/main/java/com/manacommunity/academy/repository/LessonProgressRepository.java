package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.LessonProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgressEntity, Long> {
    Optional<LessonProgressEntity> findByStudentUserIdAndLessonId(Long studentUserId, Long lessonId);
    List<LessonProgressEntity> findByStudentUserId(Long studentUserId);
    int countByStudentUserIdAndIsCompletedTrue(Long studentUserId);
}