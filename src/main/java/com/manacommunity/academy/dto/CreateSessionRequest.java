package com.manacommunity.academy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSessionRequest {

    private Integer sessionOrder;

    @NotBlank(message = "Session title is required")
    private String title;

    private String description;
    private LocalDate sessionDate;
    private String startTime;
    private String endTime;
    private String location;
    private String meetingLink;
}
