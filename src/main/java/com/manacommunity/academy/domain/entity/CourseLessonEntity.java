package com.manacommunity.academy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "academy_course_lessons", indexes = {
        @Index(name = "idx_lesson_module", columnList = "module_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseLessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_id", nullable = false)
    private Long moduleId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "lesson_type", nullable = false, length = 30)
    @Builder.Default
    private String lessonType = "VIDEO"; // VIDEO, TEXT, PDF, INTERACTIVE

    @Column(name = "content_url", length = 1024)
    private String contentUrl;

    @Column(name = "content_text", columnDefinition = "TEXT")
    private String contentText;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "sequence_order", nullable = false)
    @Builder.Default
    private Integer sequenceOrder = 1;

    @Column(name = "is_previewable", nullable = false)
    @Builder.Default
    private Boolean isPreviewable = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (lessonType == null) lessonType = "VIDEO";
        if (sequenceOrder == null) sequenceOrder = 1;
        if (isPreviewable == null) isPreviewable = false;
    }
}