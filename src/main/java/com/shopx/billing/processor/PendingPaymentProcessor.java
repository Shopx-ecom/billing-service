package com.shopx.billing.processor;

import com.shopx.billing.Payment;
import com.shopx.billing.PaymentEvent;
import com.shopx.billing.Paymentservice;
import com.shopx.billing.core.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Sameer Shaikh
 * @date 11-05-2026
 * @description
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class PendingPaymentProcessor implements PaymentProcessor{

    private final Paymentservice paymentservice;

    @Override
    public PaymentStatus getType() {
        return PaymentStatus.PENDING;
    }

    @Override
    public void process(PaymentEvent paymentEvent) {

        Payment payment = Payment.builder()
                .orderId(paymentEvent.getOrderId())
                .customerId(paymentEvent.getCustomerId())
                .status(paymentEvent.getStatus())
                .amount(paymentEvent.getAmount())
                .currency(paymentEvent.getCurrency())
                .paymentMethod(paymentEvent.getPaymentMethod())
                .currency(paymentEvent.getCurrency())
                .build();

        Payment paymentStored =  paymentservice.create(payment);

        log.info("Payment created : {}",paymentStored.toString());

    }
}
