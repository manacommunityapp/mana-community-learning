package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.CourseLessonEntity;
import com.manacommunity.academy.domain.entity.CourseModuleEntity;
import com.manacommunity.academy.domain.entity.LessonProgressEntity;
import com.manacommunity.academy.repository.CourseLessonRepository;
import com.manacommunity.academy.repository.CourseModuleRepository;
import com.manacommunity.academy.repository.LessonProgressRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CourseCurriculumService unit tests")
class CourseCurriculumServiceTest {

    @Mock private CourseModuleRepository moduleRepository;
    @Mock private CourseLessonRepository lessonRepository;
    @Mock private LessonProgressRepository progressRepository;
    @Mock private GamificationService gamificationService;
    @InjectMocks private CourseCurriculumService curriculumService;

    @Test
    @DisplayName("recordLessonProgress: marks lesson complete and awards XP")
    void recordLessonProgress_completesAndAwardsXp() {
        LessonProgressEntity existing = LessonProgressEntity.builder()
                .id(1L)
                .studentUserId(42L)
                .lessonId(101L)
                .isCompleted(false)
                .build();

        when(progressRepository.findByStudentUserIdAndLessonId(42L, 101L)).thenReturn(Optional.of(existing));
        when(progressRepository.save(any(LessonProgressEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        LessonProgressEntity result = curriculumService.recordLessonProgress(1L, 42L, 101L, true, 300);

        assertThat(result.getIsCompleted()).isTrue();
        assertThat(result.getLastPositionSeconds()).isEqualTo(300);
        assertThat(result.getCompletedAt()).isNotNull();
        verify(gamificationService).awardXp(1L, 42L, 25, "LESSON_COMPLETED");
    }
}