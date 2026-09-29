package com.manacommunity.academy.dto;

import com.manacommunity.academy.domain.entity.AcademyProgramSessionEntity;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionResponseDto {

    private String id;
    private String programId;
    private Integer sessionOrder;
    private String title;
    private String description;
    private LocalDate sessionDate;
    private String startTime;
    private String endTime;
    private String location;
    private String meetingLink;
    private String qrCheckInToken;
    private Boolean completed;

    public static SessionResponseDto fromEntity(AcademyProgramSessionEntity s) {
        if (s == null) return null;
        return SessionResponseDto.builder()
                .id(s.getId())
                .programId(s.getProgram() != null ? s.getProgram().getId() : null)
                .sessionOrder(s.getSessionOrder())
                .title(s.getTitle())
                .description(s.getDescription())
                .sessionDate(s.getSessionDate())
                .startTime(s.getStartTime())
                .endTime(s.getEndTime())
                .location(s.getLocation())
                .meetingLink(s.getMeetingLink())
                .qrCheckInToken(s.getQrCheckInToken())
                .completed(s.getCompleted())
                .build();
    }
}
