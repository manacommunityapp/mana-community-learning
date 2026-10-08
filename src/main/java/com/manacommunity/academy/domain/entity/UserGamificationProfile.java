package com.manacommunity.academy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "academy_gamification_profiles", indexes = {
        @Index(name = "idx_gam_community_user", columnList = "community_id, user_id"),
        @Index(name = "idx_gam_xp", columnList = "xp_points")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGamificationProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "community_id", nullable = false)
    private Long communityId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "xp_points", nullable = false)
    @Builder.Default
    private Integer xpPoints = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer level = 1;

    /** JSON array of earned badge identifiers, e.g. ["FIRST_LESSON", "QUIZ_MASTER", "COURSE_COMPLETE"] */
    @Column(name = "badges_json", columnDefinition = "TEXT")
    @Builder.Default
    private String badgesJson = "[]";

    @Column(name = "streak_days", nullable = false)
    @Builder.Default
    private Integer streakDays = 0;

    @Column(name = "last_active_date")
    private LocalDate lastActiveDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        updatedAt = LocalDateTime.now();
        if (xpPoints == null) xpPoints = 0;
        if (level == null) level = 1;
        if (streakDays == null) streakDays = 0;
        if (badgesJson == null) badgesJson = "[]";
    }

    public void addXp(int amount) {
        this.xpPoints = (this.xpPoints == null ? 0 : this.xpPoints) + amount;
        // Level progression: 100 XP per level
        this.level = 1 + (this.xpPoints / 100);
    }
}