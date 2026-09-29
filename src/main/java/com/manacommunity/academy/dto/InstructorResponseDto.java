package com.manacommunity.academy.dto;

import com.manacommunity.academy.domain.entity.AcademyInstructorEntity;
import com.manacommunity.academy.domain.enums.InstructorStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorResponseDto {

    private String id;
    private String communityId;
    private String residentUserId;
    private String fullName;
    private String profession;
    private String bio;
    private String profilePicUrl;
    private String tower;
    private String flatNumber;
    private String skills;
    private Integer experienceYears;
    private InstructorStatus status;
    private Integer totalSessions;
    private Integer totalLearners;
    private Double averageRating;
    private Integer reviewCount;
    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;

    public static InstructorResponseDto fromEntity(AcademyInstructorEntity i) {
        if (i == null) return null;
        return InstructorResponseDto.builder()
                .id(i.getId())
                .communityId(i.getCommunityId())
                .residentUserId(i.getResidentUserId())
                .fullName(i.getFullName())
                .profession(i.getProfession())
                .bio(i.getBio())
                .profilePicUrl(i.getProfilePicUrl())
                .tower(i.getTower())
                .flatNumber(i.getFlatNumber())
                .skills(i.getSkills())
                .experienceYears(i.getExperienceYears())
                .status(i.getStatus())
                .totalSessions(i.getTotalSessions())
                .totalLearners(i.getTotalLearners())
                .averageRating(i.getAverageRating())
                .reviewCount(i.getReviewCount())
                .approvedAt(i.getApprovedAt())
                .createdAt(i.getCreatedAt())
                .build();
    }
}
