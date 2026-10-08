package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.CourseQuizEntity;
import com.manacommunity.academy.domain.entity.QuizQuestionEntity;
import com.manacommunity.academy.domain.entity.StudentSubmissionEntity;
import com.manacommunity.academy.repository.CourseAssignmentRepository;
import com.manacommunity.academy.repository.CourseQuizRepository;
import com.manacommunity.academy.repository.QuizQuestionRepository;
import com.manacommunity.academy.repository.StudentSubmissionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CourseAssessmentService unit tests")
class CourseAssessmentServiceTest {

    @Mock private CourseAssignmentRepository assignmentRepository;
    @Mock private StudentSubmissionRepository submissionRepository;
    @Mock private CourseQuizRepository quizRepository;
    @Mock private QuizQuestionRepository questionRepository;
    @Mock private GamificationService gamificationService;
    @InjectMocks private CourseAssessmentService assessmentService;

    @Test
    @DisplayName("evaluateQuiz: calculates percentage score and awards XP on pass")
    void evaluateQuiz_passingScore_awardsXp() {
        CourseQuizEntity quiz = CourseQuizEntity.builder()
                .id(1L)
                .moduleId(10L)
                .title("Midterm Quiz")
                .passingScore(70)
                .build();

        QuizQuestionEntity q1 = QuizQuestionEntity.builder()
                .id(101L).quizId(1L).questionText("Q1").optionsJson("[]").correctOptionIndex(1).points(10).build();
        QuizQuestionEntity q2 = QuizQuestionEntity.builder()
                .id(102L).quizId(1L).questionText("Q2").optionsJson("[]").correctOptionIndex(2).points(10).build();

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(questionRepository.findByQuizId(1L)).thenReturn(List.of(q1, q2));

        // Submit correct answers for both: [1, 2] -> 100%
        Map<String, Object> result = assessmentService.evaluateQuiz(1L, 5L, 42L, List.of(1, 2));

        assertThat(result.get("score")).isEqualTo(100);
        assertThat(result.get("passed")).isEqualTo(true);
        assertThat(result.get("correctCount")).isEqualTo(2);
        verify(gamificationService).awardXp(5L, 42L, 50, "QUIZ_PASSED");
    }

    @Test
    @DisplayName("gradeSubmission: saves grade and awards XP if score >= 60")
    void gradeSubmission_passingGrade_awardsXp() {
        StudentSubmissionEntity submission = StudentSubmissionEntity.builder()
                .id(50L)
                .assignmentId(5L)
                .studentUserId(42L)
                .status("SUBMITTED")
                .build();

        when(submissionRepository.findById(50L)).thenReturn(Optional.of(submission));
        when(submissionRepository.save(any(StudentSubmissionEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        StudentSubmissionEntity graded = assessmentService.gradeSubmission(50L, 85, "Great work!", 1L);

        assertThat(graded.getStatus()).isEqualTo("GRADED");
        assertThat(graded.getScore()).isEqualTo(85);
        assertThat(graded.getFeedback()).isEqualTo("Great work!");
        verify(gamificationService).awardXp(1L, 42L, 40, "ASSIGNMENT_PASSED");
    }
}