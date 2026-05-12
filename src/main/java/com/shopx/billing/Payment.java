package com.shopx.billing;

import com.shopx.billing.core.BaseEntity;
import com.shopx.billing.core.enums.PaymentStatus;
import com.shopx.billing.core.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "currency")
    @Builder.Default
    private String currency = "INR";
}