package com.manacommunity.academy.dto;

import com.manacommunity.academy.domain.entity.AcademyProgramEntity;
import com.manacommunity.academy.domain.entity.AcademyProgramSessionEntity;
import com.manacommunity.academy.domain.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramResponseDto {

    private String id;
    private String communityId;
    private String instructorId;
    private String instructorName;
    private String categoryId;
    private String categoryName;
    private String title;
    private String summary;
    private String description;
    private String coverImageUrl;
    private LearningType learningType;
    private ProgramLevel level;
    private ProgramMode mode;
    private String location;
    private String onlineMeetingUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startTime;
    private Integer durationMinutes;
    private Integer capacity;
    private Integer enrolledCount;
    private Integer waitlistCount;
    private Integer availableSeats;
    private Boolean isFull;
    private PricingType pricingType;
    private BigDecimal price;
    private String prerequisites;
    private String targetAudience;
    private String tags;
    private Boolean certificateEnabled;
    private ProgramStatus status;
    private Double averageRating;
    private Integer reviewCount;
    private List<SessionResponseDto> sessions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProgramResponseDto fromEntity(AcademyProgramEntity p) {
        if (p == null) return null;
        return ProgramResponseDto.builder()
                .id(p.getId())
                .communityId(p.getCommunityId())
                .instructorId(p.getInstructorId())
                .instructorName(p.getInstructorName())
                .categoryId(p.getCategoryId())
                .categoryName(p.getCategoryName())
                .title(p.getTitle())
                .summary(p.getSummary())
                .description(p.getDescription())
                .coverImageUrl(p.getCoverImageUrl())
                .learningType(p.getLearningType())
                .level(p.getLevel())
                .mode(p.getMode())
                .location(p.getLocation())
                .onlineMeetingUrl(p.getOnlineMeetingUrl())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .startTime(p.getStartTime())
                .durationMinutes(p.getDurationMinutes())
                .capacity(p.getCapacity())
                .enrolledCount(p.getEnrolledCount())
                .waitlistCount(p.getWaitlistCount())
                .availableSeats(p.getAvailableSeats())
                .isFull(p.isFull())
                .pricingType(p.getPricingType())
                .price(p.getPrice())
                .prerequisites(p.getPrerequisites())
                .targetAudience(p.getTargetAudience())
                .tags(p.getTags())
                .certificateEnabled(p.getCertificateEnabled())
                .status(p.getStatus())
                .averageRating(p.getAverageRating())
                .reviewCount(p.getReviewCount())
                .sessions(p.getSessions() != null ? p.getSessions().stream()
                        .map(SessionResponseDto::fromEntity)
                        .collect(Collectors.toList()) : Collections.emptyList())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
