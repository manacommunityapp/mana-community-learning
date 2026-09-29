package com.manacommunity.academy.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "academy_certificate", schema = "manacommunity",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_acad_cert_prog_user", columnNames = {"program_id", "user_id"})
    },
    indexes = {
        @Index(name = "idx_acad_cert_user", columnList = "user_id")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademyCertificateEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "certificate_number", nullable = false, unique = true, length = 100)
    private String certificateNumber;

    @Column(name = "program_id", length = 64, nullable = false)
    private String programId;

    @Column(name = "program_title", length = 200, nullable = false)
    private String programTitle;

    @Column(name = "instructor_name", length = 120, nullable = false)
    private String instructorName;

    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    @Column(name = "user_name", length = 120, nullable = false)
    private String userName;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "verification_hash", length = 128)
    private String verificationHash;

    @Column(name = "certificate_url", length = 500)
    private String certificateUrl;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
