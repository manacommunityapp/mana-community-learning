package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.CourseAssignmentEntity;
import com.manacommunity.academy.domain.entity.CourseQuizEntity;
import com.manacommunity.academy.domain.entity.QuizQuestionEntity;
import com.manacommunity.academy.domain.entity.StudentSubmissionEntity;
import com.manacommunity.academy.repository.CourseAssignmentRepository;
import com.manacommunity.academy.repository.CourseQuizRepository;
import com.manacommunity.academy.repository.QuizQuestionRepository;
import com.manacommunity.academy.repository.StudentSubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseAssessmentService {

    private final CourseAssignmentRepository assignmentRepository;
    private final StudentSubmissionRepository submissionRepository;
    private final CourseQuizRepository quizRepository;
    private final QuizQuestionRepository questionRepository;
    private final GamificationService gamificationService;

    @Transactional
    public CourseAssignmentEntity createAssignment(CourseAssignmentEntity assignment) {
        return assignmentRepository.save(assignment);
    }

    @Transactional
    public StudentSubmissionEntity submitAssignment(Long assignmentId, Long studentUserId,
                                                    String text, String attachmentUrl) {
        StudentSubmissionEntity submission = submissionRepository
                .findByAssignmentIdAndStudentUserId(assignmentId, studentUserId)
                .orElseGet(() -> StudentSubmissionEntity.builder()
                        .assignmentId(assignmentId)
                        .studentUserId(studentUserId)
                        .build());

        submission.setSubmissionText(text);
        submission.setAttachmentUrl(attachmentUrl);
        submission.setStatus("SUBMITTED");
        return submissionRepository.save(submission);
    }

    @Transactional
    public StudentSubmissionEntity gradeSubmission(Long submissionId, int score, String feedback, Long communityId) {
        StudentSubmissionEntity submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found: " + submissionId));

        submission.setScore(score);
        submission.setFeedback(feedback);
        submission.setStatus("GRADED");
        submission.setGradedAt(LocalDateTime.now());

        if (score >= 60) {
            gamificationService.awardXp(communityId, submission.getStudentUserId(), 40, "ASSIGNMENT_PASSED");
        }

        return submissionRepository.save(submission);
    }

    @Transactional
    public CourseQuizEntity createQuiz(CourseQuizEntity quiz, List<QuizQuestionEntity> questions) {
        CourseQuizEntity savedQuiz = quizRepository.save(quiz);
        if (questions != null) {
            for (QuizQuestionEntity q : questions) {
                q.setQuizId(savedQuiz.getId());
                questionRepository.save(q);
            }
        }
        return savedQuiz;
    }

    @Transactional
    public Map<String, Object> evaluateQuiz(Long quizId, Long communityId, Long studentUserId,
                                            List<Integer> submittedAnswers) {
        CourseQuizEntity quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found: " + quizId));

        List<QuizQuestionEntity> questions = questionRepository.findByQuizId(quizId);
        int correct = 0;
        int total = questions.size();

        for (int i = 0; i < total; i++) {
            if (submittedAnswers != null && i < submittedAnswers.size()) {
                if (questions.get(i).getCorrectOptionIndex().equals(submittedAnswers.get(i))) {
                    correct++;
                }
            }
        }

        int percentage = total > 0 ? (correct * 100) / total : 0;
        boolean passed = percentage >= quiz.getPassingScore();

        if (passed) {
            gamificationService.awardXp(communityId, studentUserId, 50, "QUIZ_PASSED");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("quizId", quizId);
        result.put("studentUserId", studentUserId);
        result.put("score", percentage);
        result.put("passed", passed);
        result.put("correctCount", correct);
        result.put("totalQuestions", total);
        return result;
    }
}