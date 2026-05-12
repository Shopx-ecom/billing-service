package com.shopx.billing;

import com.shopx.billing.core.enums.PaymentMethod;
import com.shopx.billing.core.enums.PaymentStatus;
import lombok.Data;

import java.util.List;

@Data
public class PaymentFilter {

    private Long id;
    private List<Long> ids;

    private Long orderId;
    private Long customerId;
    private Double amount;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private String transactionId;
    private String currency;

    private String search;
}