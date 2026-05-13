package com.shopx.billing;

import com.shopx.common.enums.PaymentMethod;
import com.shopx.common.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEvent {

    private String topic;       // payment.success | payment.failed
    private Long paymentId;
    private Long orderId;
    private Long customerId;
    private Double amount;
    private String currency;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private String transactionId;
    private String failureReason;
    private LocalDateTime occurredAt;
}