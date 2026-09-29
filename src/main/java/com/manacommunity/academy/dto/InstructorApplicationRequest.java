package com.manacommunity.academy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorApplicationRequest {

    @NotBlank(message = "Community ID is required")
    private String communityId;

    @NotBlank(message = "Resident user ID is required")
    private String residentUserId;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String profession;
    private String bio;
    private String profilePicUrl;
    private String tower;
    private String flatNumber;

    @NotBlank(message = "Skills are required")
    private String skills;

    private Integer experienceYears;
}
