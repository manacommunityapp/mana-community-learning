package com.manacommunity.academy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "academy_lesson_progress", indexes = {
        @Index(name = "idx_progress_student", columnList = "student_user_id"),
        @Index(name = "idx_progress_lesson", columnList = "lesson_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_user_id", nullable = false)
    private Long studentUserId;

    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @Column(name = "is_completed", nullable = false)
    @Builder.Default
    private Boolean isCompleted = false;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "last_position_seconds")
    @Builder.Default
    private Integer lastPositionSeconds = 0;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        updatedAt = LocalDateTime.now();
        if (isCompleted == null) isCompleted = false;
        if (lastPositionSeconds == null) lastPositionSeconds = 0;
    }
}