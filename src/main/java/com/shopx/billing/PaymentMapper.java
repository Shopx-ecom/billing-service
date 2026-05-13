package com.shopx.billing;

import com.shopx.common.enums.PaymentStatus;
import com.shopx.billing.dto.PaymentRequestDto;
import com.shopx.billing.dto.PaymentResponseDto;
import com.shopx.billing.dto.PaymentUpdateDto;

import java.util.HashMap;
import java.util.Map;

public class PaymentMapper {

    public static Payment toEntity(PaymentRequestDto dto, String transactionId) {
        PaymentStatus status = resolveStatus(dto.getPaymentStatus());
        return Payment.builder()
                .orderId(dto.getOrderId())
                .customerId(dto.getCustomerId())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .paymentMethod(dto.getPaymentMethod())
                .status(status)
                .transactionId(transactionId)
                .build();
    }

    public static PaymentResponseDto toResponse(Payment payment) {
        return PaymentResponseDto.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .customerId(payment.getCustomerId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .failureReason(payment.getFailureReason())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    // Maps mock PAID/UNPAID flag to actual PaymentStatus
    private static PaymentStatus resolveStatus(PaymentStatus requestedStatus) {
        return switch (requestedStatus) {
            case SUCCESS -> PaymentStatus.SUCCESS;
            case FAILED  -> PaymentStatus.FAILED;
            default      -> PaymentStatus.PENDING;
        };
    }

    public static Map<String, Object> toMap(PaymentUpdateDto dto) {

        Map<String, Object> map = new HashMap<>();

        if (dto.getAmount() != null) {
            map.put("amount", dto.getAmount());
        }

        if (dto.getPaymentStatus() != null) {
            map.put("paymentStatus", dto.getPaymentStatus());
        }

        if (dto.getPaymentMethod() != null) {
            map.put("paymentMethod", dto.getPaymentMethod());
        }

        if (dto.getCurrency() != null) {
            map.put("currency", dto.getCurrency());
        }

        return map;
    }
}