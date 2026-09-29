package com.manacommunity.academy.dto;

import com.manacommunity.academy.domain.entity.AcademyReviewEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDto {

    private String id;
    private String programId;
    private String instructorId;
    private String userId;
    private String userName;
    private Integer overallRating;
    private Integer instructorRating;
    private Integer contentRating;
    private String reviewComment;
    private Boolean wouldRecommend;
    private LocalDateTime createdAt;

    public static ReviewResponseDto fromEntity(AcademyReviewEntity r) {
        if (r == null) return null;
        return ReviewResponseDto.builder()
                .id(r.getId())
                .programId(r.getProgramId())
                .instructorId(r.getInstructorId())
                .userId(r.getUserId())
                .userName(r.getUserName())
                .overallRating(r.getOverallRating())
                .instructorRating(r.getInstructorRating())
                .contentRating(r.getContentRating())
                .reviewComment(r.getReviewComment())
                .wouldRecommend(r.getWouldRecommend())
                .createdAt(r.getCreatedAt())
                .build();
    }
}
