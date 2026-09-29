package com.manacommunity.academy.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "academy_waitlist", schema = "manacommunity",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_acad_waitlist_prog_user", columnNames = {"program_id", "user_id"})
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademyWaitlistEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "program_id", length = 64, nullable = false)
    private String programId;

    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    @Column(name = "user_name", length = 120, nullable = false)
    private String userName;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "is_notified")
    @Builder.Default
    private Boolean notified = false;

    @Column(name = "notified_at")
    private LocalDateTime notifiedAt;

    @Column(name = "is_claimed")
    @Builder.Default
    private Boolean claimed = false;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
