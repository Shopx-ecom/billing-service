package com.shopx.billing.dto;

import com.shopx.billing.core.enums.PaymentMethod;
import com.shopx.billing.core.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

/**
 * @author Sameer Shaikh
 * @date 11-05-2026
 * @description
 */

@Data
@Builder
public class PaymentUpdateDto {

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
