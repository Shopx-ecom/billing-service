package com.shopx.billing.dto;

import com.shopx.common.enums.PaymentMethod;
import com.shopx.common.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;

    @Builder.Default
    private PaymentMethod paymentMethod = PaymentMethod.MOCK;

    @Builder.Default
    private String currency = "INR";
}