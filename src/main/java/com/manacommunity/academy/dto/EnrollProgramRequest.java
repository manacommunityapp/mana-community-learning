package com.manacommunity.academy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollProgramRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "User name is required")
    private String userName;

    private String userEmail;
    private String userPhone;
    private String tower;
    private String flatNumber;
    private BigDecimal amountPaid;
    private String paymentId;
}
