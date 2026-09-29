package com.manacommunity.academy.dto;

import com.manacommunity.academy.domain.entity.AcademyCertificateEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateResponseDto {

    private String id;
    private String certificateNumber;
    private String programId;
    private String programTitle;
    private String instructorName;
    private String userId;
    private String userName;
    private LocalDate issueDate;
    private String verificationHash;
    private String certificateUrl;
    private LocalDateTime createdAt;

    public static CertificateResponseDto fromEntity(AcademyCertificateEntity c) {
        if (c == null) return null;
        return CertificateResponseDto.builder()
                .id(c.getId())
                .certificateNumber(c.getCertificateNumber())
                .programId(c.getProgramId())
                .programTitle(c.getProgramTitle())
                .instructorName(c.getInstructorName())
                .userId(c.getUserId())
                .userName(c.getUserName())
                .issueDate(c.getIssueDate())
                .verificationHash(c.getVerificationHash())
                .certificateUrl(c.getCertificateUrl())
                .createdAt(c.getCreatedAt())
                .build();
    }
}
