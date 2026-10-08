package com.manacommunity.academy.controller;

import com.manacommunity.academy.domain.entity.CourseAssignmentEntity;
import com.manacommunity.academy.domain.entity.CourseQuizEntity;
import com.manacommunity.academy.domain.entity.QuizQuestionEntity;
import com.manacommunity.academy.domain.entity.StudentSubmissionEntity;
import com.manacommunity.academy.service.CourseAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/academy/assessments")
@RequiredArgsConstructor
public class CourseAssessmentController {

    private final CourseAssessmentService assessmentService;

    @PostMapping("/assignments")
    public ResponseEntity<CourseAssignmentEntity> createAssignment(@RequestBody CourseAssignmentEntity assignment) {
        return ResponseEntity.ok(assessmentService.createAssignment(assignment));
    }

    @PostMapping("/assignments/{assignmentId}/submit")
    public ResponseEntity<StudentSubmissionEntity> submitAssignment(
            @PathVariable Long assignmentId,
            @RequestParam Long studentUserId,
            @RequestBody Map<String, String> body) {
        String text = body.get("text");
        String attachmentUrl = body.get("attachmentUrl");
        return ResponseEntity.ok(assessmentService.submitAssignment(assignmentId, studentUserId, text, attachmentUrl));
    }

    @PostMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<StudentSubmissionEntity> gradeSubmission(
            @PathVariable Long submissionId,
            @RequestParam Long communityId,
            @RequestBody Map<String, Object> body) {
        int score = Integer.parseInt(body.get("score").toString());
        String feedback = (String) body.get("feedback");
        return ResponseEntity.ok(assessmentService.gradeSubmission(submissionId, score, feedback, communityId));
    }

    @PostMapping("/quizzes")
    public ResponseEntity<CourseQuizEntity> createQuiz(
            @RequestBody CourseQuizEntity quiz,
            @RequestParam(required = false) List<QuizQuestionEntity> questions) {
        return ResponseEntity.ok(assessmentService.createQuiz(quiz, questions));
    }

    @PostMapping("/quizzes/{quizId}/evaluate")
    public ResponseEntity<Map<String, Object>> evaluateQuiz(
            @PathVariable Long quizId,
            @RequestParam Long communityId,
            @RequestParam Long studentUserId,
            @RequestBody List<Integer> submittedAnswers) {
        return ResponseEntity.ok(assessmentService.evaluateQuiz(quizId, communityId, studentUserId, submittedAnswers));
    }
}