package com.manacommunity.academy.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitReviewRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "User name is required")
    private String userName;

    @NotNull(message = "Rating is required")
    @Min(1)
    @Max(5)
    private Integer overallRating;

    private Integer instructorRating;
    private Integer contentRating;
    private String reviewComment;
    private Boolean wouldRecommend;
}
