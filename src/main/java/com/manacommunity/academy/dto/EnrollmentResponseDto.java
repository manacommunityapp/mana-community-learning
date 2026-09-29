package com.manacommunity.academy.dto;

import com.manacommunity.academy.domain.entity.AcademyEnrollmentEntity;
import com.manacommunity.academy.domain.enums.EnrollmentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponseDto {

    private String id;
    private String programId;
    private String programTitle;
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String tower;
    private String flatNumber;
    private Integer seatNumber;
    private EnrollmentStatus status;
    private BigDecimal amountPaid;
    private String paymentId;
    private String qrPassCode;
    private LocalDateTime enrolledAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime createdAt;

    public static EnrollmentResponseDto fromEntity(AcademyEnrollmentEntity e) {
        if (e == null) return null;
        return EnrollmentResponseDto.builder()
                .id(e.getId())
                .programId(e.getProgramId())
                .programTitle(e.getProgramTitle())
                .userId(e.getUserId())
                .userName(e.getUserName())
                .userEmail(e.getUserEmail())
                .userPhone(e.getUserPhone())
                .tower(e.getTower())
                .flatNumber(e.getFlatNumber())
                .seatNumber(e.getSeatNumber())
                .status(e.getStatus())
                .amountPaid(e.getAmountPaid())
                .paymentId(e.getPaymentId())
                .qrPassCode(e.getQrPassCode())
                .enrolledAt(e.getEnrolledAt())
                .cancelledAt(e.getCancelledAt())
                .createdAt(e.getCreatedAt())
                .build();
    }
}
