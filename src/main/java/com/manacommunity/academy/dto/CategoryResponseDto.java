package com.manacommunity.academy.dto;

import com.manacommunity.academy.domain.entity.AcademyCategoryEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {

    private String id;
    private String name;
    private String code;
    private String description;
    private String icon;
    private Integer displayOrder;
    private Boolean active;
    private LocalDateTime createdAt;

    public static CategoryResponseDto fromEntity(AcademyCategoryEntity c) {
        if (c == null) return null;
        return CategoryResponseDto.builder()
                .id(c.getId())
                .name(c.getName())
                .code(c.getCode())
                .description(c.getDescription())
                .icon(c.getIcon())
                .displayOrder(c.getDisplayOrder())
                .active(c.getActive())
                .createdAt(c.getCreatedAt())
                .build();
    }
}
