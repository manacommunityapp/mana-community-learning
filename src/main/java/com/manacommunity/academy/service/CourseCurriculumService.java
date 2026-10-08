package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.CourseLessonEntity;
import com.manacommunity.academy.domain.entity.CourseModuleEntity;
import com.manacommunity.academy.domain.entity.LessonProgressEntity;
import com.manacommunity.academy.repository.CourseLessonRepository;
import com.manacommunity.academy.repository.CourseModuleRepository;
import com.manacommunity.academy.repository.LessonProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseCurriculumService {

    private final CourseModuleRepository moduleRepository;
    private final CourseLessonRepository lessonRepository;
    private final LessonProgressRepository progressRepository;
    private final GamificationService gamificationService;

    @Transactional
    public CourseModuleEntity createModule(CourseModuleEntity module) {
        return moduleRepository.save(module);
    }

    @Transactional(readOnly = true)
    public List<CourseModuleEntity> getModulesForProgram(Long programId) {
        return moduleRepository.findByProgramIdOrderBySequenceOrderAsc(programId);
    }

    @Transactional
    public CourseLessonEntity createLesson(CourseLessonEntity lesson) {
        return lessonRepository.save(lesson);
    }

    @Transactional(readOnly = true)
    public List<CourseLessonEntity> getLessonsForModule(Long moduleId) {
        return lessonRepository.findByModuleIdOrderBySequenceOrderAsc(moduleId);
    }

    @Transactional
    public LessonProgressEntity recordLessonProgress(Long communityId, Long studentUserId, Long lessonId,
                                                     boolean completed, int positionSeconds) {
        LessonProgressEntity progress = progressRepository.findByStudentUserIdAndLessonId(studentUserId, lessonId)
                .orElseGet(() -> LessonProgressEntity.builder()
                        .studentUserId(studentUserId)
                        .lessonId(lessonId)
                        .isCompleted(false)
                        .lastPositionSeconds(0)
                        .build());

        progress.setLastPositionSeconds(positionSeconds);
        if (completed && !Boolean.TRUE.equals(progress.getIsCompleted())) {
            progress.setIsCompleted(true);
            progress.setCompletedAt(LocalDateTime.now());
            // Award 25 XP for completing lesson
            gamificationService.awardXp(communityId, studentUserId, 25, "LESSON_COMPLETED");
        }

        return progressRepository.save(progress);
    }
}