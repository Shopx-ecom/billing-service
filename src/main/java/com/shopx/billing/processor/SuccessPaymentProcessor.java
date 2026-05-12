package com.shopx.billing.processor;

import com.shopx.billing.Payment;
import com.shopx.billing.PaymentEvent;
import com.shopx.billing.core.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class SuccessPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentStatus getType() {
        return PaymentStatus.SUCCESS;
    }

    @Override
    public void process(PaymentEvent paymentEvent) {

        System.out.println("Payment Success");

    }
}