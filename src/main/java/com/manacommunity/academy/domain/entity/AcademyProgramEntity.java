package com.manacommunity.academy.domain.entity;

import com.manacommunity.academy.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "academy_program", schema = "manacommunity", indexes = {
    @Index(name = "idx_acad_prog_comm", columnList = "community_id"),
    @Index(name = "idx_acad_prog_cat", columnList = "category_id"),
    @Index(name = "idx_acad_prog_instr", columnList = "instructor_id"),
    @Index(name = "idx_acad_prog_status", columnList = "status")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademyProgramEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "community_id", length = 64, nullable = false)
    private String communityId;

    @Column(name = "instructor_id", length = 64, nullable = false)
    private String instructorId;

    @Column(name = "instructor_name", length = 120, nullable = false)
    private String instructorName;

    @Column(name = "category_id", length = 64, nullable = false)
    private String categoryId;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "summary", length = 500)
    private String summary;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "learning_type", length = 50, nullable = false)
    @Builder.Default
    private LearningType learningType = LearningType.WORKSHOP;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", length = 50, nullable = false)
    @Builder.Default
    private ProgramLevel level = ProgramLevel.ALL_LEVELS;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", length = 50, nullable = false)
    @Builder.Default
    private ProgramMode mode = ProgramMode.IN_PERSON;

    @Column(name = "location", length = 250)
    private String location; // e.g. "Clubhouse Multi-purpose Hall" or "Zoom link on enrollment"

    @Column(name = "online_meeting_url", length = 500)
    private String onlineMeetingUrl;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time", length = 50)
    private String startTime; // e.g. "17:00"

    @Column(name = "duration_minutes")
    private Integer durationMinutes; // e.g. 120

    @Column(name = "capacity", nullable = false)
    @Builder.Default
    private Integer capacity = 20;

    @Column(name = "enrolled_count", nullable = false)
    @Builder.Default
    private Integer enrolledCount = 0;

    @Column(name = "waitlist_count", nullable = false)
    @Builder.Default
    private Integer waitlistCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "pricing_type", length = 50, nullable = false)
    @Builder.Default
    private PricingType pricingType = PricingType.FREE;

    @Column(name = "price", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "prerequisites", length = 1000)
    private String prerequisites;

    @Column(name = "target_audience", length = 500)
    private String targetAudience;

    @Column(name = "tags", length = 500)
    private String tags;

    @Column(name = "certificate_enabled")
    @Builder.Default
    private Boolean certificateEnabled = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    @Builder.Default
    private ProgramStatus status = ProgramStatus.PUBLISHED;

    @Column(name = "average_rating")
    @Builder.Default
    private Double averageRating = 5.0;

    @Column(name = "review_count")
    @Builder.Default
    private Integer reviewCount = 0;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sessionOrder ASC")
    @Builder.Default
    private List<AcademyProgramSessionEntity> sessions = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public boolean isFull() {
        return enrolledCount >= capacity;
    }

    public int getAvailableSeats() {
        return Math.max(0, capacity - enrolledCount);
    }
}
