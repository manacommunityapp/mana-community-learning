package com.manacommunity.academy.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "academy_material", schema = "manacommunity", indexes = {
    @Index(name = "idx_acad_mat_prog", columnList = "program_id")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademyMaterialEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "program_id", length = 64, nullable = false)
    private String programId;

    @Column(name = "session_id", length = 64)
    private String sessionId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "file_type", length = 50)
    private String fileType; // PDF, PPT, DOC, VIDEO, LINK

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    @Column(name = "s3_storage_key", length = 500)
    private String s3StorageKey;

    @Column(name = "external_url", length = 1000)
    private String externalUrl;

    @Column(name = "uploaded_by_id", length = 64)
    private String uploadedById;

    @Column(name = "uploaded_by_name", length = 120)
    private String uploadedByName;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
