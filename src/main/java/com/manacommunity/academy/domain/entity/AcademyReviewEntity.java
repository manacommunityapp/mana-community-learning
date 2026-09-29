package com.manacommunity.academy.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "academy_review", schema = "manacommunity",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_acad_rev_prog_user", columnNames = {"program_id", "user_id"})
    },
    indexes = {
        @Index(name = "idx_acad_rev_prog", columnList = "program_id"),
        @Index(name = "idx_acad_rev_instr", columnList = "instructor_id")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademyReviewEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "program_id", length = 64, nullable = false)
    private String programId;

    @Column(name = "instructor_id", length = 64, nullable = false)
    private String instructorId;

    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    @Column(name = "user_name", length = 120, nullable = false)
    private String userName;

    @Column(name = "overall_rating", nullable = false)
    private Integer overallRating; // 1-5

    @Column(name = "instructor_rating")
    private Integer instructorRating; // 1-5

    @Column(name = "content_rating")
    private Integer contentRating; // 1-5

    @Column(name = "review_comment", length = 1500)
    private String reviewComment;

    @Column(name = "would_recommend")
    @Builder.Default
    private Boolean wouldRecommend = true;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
