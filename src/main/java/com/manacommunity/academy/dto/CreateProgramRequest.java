package com.manacommunity.academy.dto;

import com.manacommunity.academy.domain.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProgramRequest {

    @NotBlank(message = "Community ID is required")
    private String communityId;

    @NotBlank(message = "Instructor ID is required")
    private String instructorId;

    @NotBlank(message = "Category ID is required")
    private String categoryId;

    @NotBlank(message = "Title is required")
    private String title;

    private String summary;
    private String description;
    private String coverImageUrl;

    @NotNull(message = "Learning type is required")
    private LearningType learningType;

    private ProgramLevel level;
    private ProgramMode mode;
    private String location;
    private String onlineMeetingUrl;

    private LocalDate startDate;
    private LocalDate endDate;
    private String startTime;
    private Integer durationMinutes;

    @NotNull(message = "Capacity is required")
    private Integer capacity;

    private PricingType pricingType;
    private BigDecimal price;

    private String prerequisites;
    private String targetAudience;
    private String tags;
    private Boolean certificateEnabled;

    private List<CreateSessionRequest> sessions;
}
