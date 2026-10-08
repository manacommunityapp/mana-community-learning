package com.manacommunity.academy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "academy_student_submissions", indexes = {
        @Index(name = "idx_submission_assignment", columnList = "assignment_id"),
        @Index(name = "idx_submission_student", columnList = "student_user_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentSubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    @Column(name = "student_user_id", nullable = false)
    private Long studentUserId;

    @Column(name = "submission_text", columnDefinition = "TEXT")
    private String submissionText;

    @Column(name = "attachment_url", length = 1024)
    private String attachmentUrl;

    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "SUBMITTED"; // SUBMITTED, GRADED, RESUBMIT_REQUESTED

    @Column(name = "submitted_at", nullable = false, updatable = false)
    private LocalDateTime submittedAt;

    @Column(name = "graded_at")
    private LocalDateTime gradedAt;

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
        if (status == null) status = "SUBMITTED";
    }
}