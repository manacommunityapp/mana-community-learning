package com.manacommunity.academy.dto;

import com.manacommunity.academy.domain.enums.AttendanceStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarkAttendanceRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    private String userName;
    private String tower;
    private String flatNumber;
    private AttendanceStatus status;
    private String checkInMethod; // QR_SCAN, MANUAL_INSTRUCTOR, ADMIN
    private String markedBy;
    private String qrCodeScanned;
}
