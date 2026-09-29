package com.manacommunity.academy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadMaterialRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private String sessionId;
    private String fileType;
    private Long fileSizeBytes;
    private String s3StorageKey;
    private String externalUrl;
    private String uploadedById;
    private String uploadedByName;
}
