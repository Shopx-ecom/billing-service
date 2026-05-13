package com.shopx.billing.processor;

import com.shopx.common.enums.PaymentStatus;
import com.shopx.common.event.PaymentEvent;
import org.springframework.stereotype.Component;

@Component
public class FailedPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentStatus getType() {
        return PaymentStatus.FAILED;
    }

    @Override
    public void process(PaymentEvent paymentEvent) {

        System.out.println("Payment Failed");

    }
}