package com.manacommunity.academy.dto;

import com.manacommunity.academy.domain.entity.AcademyAttendanceEntity;
import com.manacommunity.academy.domain.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceResponseDto {

    private String id;
    private String sessionId;
    private String programId;
    private String userId;
    private String userName;
    private String tower;
    private String flatNumber;
    private AttendanceStatus status;
    private LocalDateTime checkInTime;
    private String checkInMethod;
    private String markedBy;
    private LocalDateTime createdAt;

    public static AttendanceResponseDto fromEntity(AcademyAttendanceEntity a) {
        if (a == null) return null;
        return AttendanceResponseDto.builder()
                .id(a.getId())
                .sessionId(a.getSessionId())
                .programId(a.getProgramId())
                .userId(a.getUserId())
                .userName(a.getUserName())
                .tower(a.getTower())
                .flatNumber(a.getFlatNumber())
                .status(a.getStatus())
                .checkInTime(a.getCheckInTime())
                .checkInMethod(a.getCheckInMethod())
                .markedBy(a.getMarkedBy())
                .createdAt(a.getCreatedAt())
                .build();
    }
}
