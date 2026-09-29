package com.manacommunity.academy.domain.entity;

import com.manacommunity.academy.domain.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "academy_attendance", schema = "manacommunity",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_acad_attend_sess_user", columnNames = {"session_id", "user_id"})
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademyAttendanceEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "session_id", length = 64, nullable = false)
    private String sessionId;

    @Column(name = "program_id", length = 64, nullable = false)
    private String programId;

    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    @Column(name = "user_name", length = 120, nullable = false)
    private String userName;

    @Column(name = "tower", length = 50)
    private String tower;

    @Column(name = "flat_number", length = 50)
    private String flatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    @Builder.Default
    private AttendanceStatus status = AttendanceStatus.PRESENT;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_in_method", length = 50)
    private String checkInMethod; // QR_SCAN, MANUAL_INSTRUCTOR, ADMIN

    @Column(name = "marked_by", length = 64)
    private String markedBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
