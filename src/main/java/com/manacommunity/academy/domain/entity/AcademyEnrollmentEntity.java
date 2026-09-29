package com.manacommunity.academy.domain.entity;

import com.manacommunity.academy.domain.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "academy_enrollment", schema = "manacommunity",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_acad_enroll_prog_user", columnNames = {"program_id", "user_id"})
    },
    indexes = {
        @Index(name = "idx_acad_enroll_user", columnList = "user_id"),
        @Index(name = "idx_acad_enroll_prog", columnList = "program_id")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademyEnrollmentEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "program_id", length = 64, nullable = false)
    private String programId;

    @Column(name = "program_title", length = 200)
    private String programTitle;

    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    @Column(name = "user_name", length = 120, nullable = false)
    private String userName;

    @Column(name = "user_email", length = 150)
    private String userEmail;

    @Column(name = "user_phone", length = 50)
    private String userPhone;

    @Column(name = "tower", length = 50)
    private String tower;

    @Column(name = "flat_number", length = 50)
    private String flatNumber;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    @Builder.Default
    private EnrollmentStatus status = EnrollmentStatus.CONFIRMED;

    @Column(name = "amount_paid", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal amountPaid = BigDecimal.ZERO;

    @Column(name = "payment_id", length = 100)
    private String paymentId;

    @Column(name = "qr_pass_code", length = 100)
    private String qrPassCode;

    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
