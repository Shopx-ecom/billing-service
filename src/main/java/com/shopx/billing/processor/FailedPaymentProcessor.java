package com.shopx.billing.processor;

import com.shopx.billing.Payment;
import com.shopx.billing.PaymentEvent;
import com.shopx.billing.core.enums.PaymentStatus;
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