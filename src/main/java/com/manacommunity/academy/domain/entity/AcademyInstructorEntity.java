package com.manacommunity.academy.domain.entity;

import com.manacommunity.academy.domain.enums.InstructorStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "academy_instructor", schema = "manacommunity")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademyInstructorEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "community_id", length = 64, nullable = false)
    private String communityId;

    @Column(name = "resident_user_id", length = 64, nullable = false)
    private String residentUserId;

    @Column(name = "full_name", length = 120, nullable = false)
    private String fullName;

    @Column(name = "profession", length = 150)
    private String profession;

    @Column(name = "bio", length = 1500)
    private String bio;

    @Column(name = "profile_pic_url", length = 500)
    private String profilePicUrl;

    @Column(name = "tower", length = 50)
    private String tower;

    @Column(name = "flat_number", length = 50)
    private String flatNumber;

    @Column(name = "skills", length = 1000)
    private String skills; // Comma-separated (e.g. "Java, Spring Boot, AWS, Docker")

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    @Builder.Default
    private InstructorStatus status = InstructorStatus.APPLIED;

    @Column(name = "total_sessions")
    @Builder.Default
    private Integer totalSessions = 0;

    @Column(name = "total_learners")
    @Builder.Default
    private Integer totalLearners = 0;

    @Column(name = "average_rating")
    @Builder.Default
    private Double averageRating = 5.0;

    @Column(name = "review_count")
    @Builder.Default
    private Integer reviewCount = 0;

    @Column(name = "approved_by", length = 64)
    private String approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
