package com.shopx.billing.processor;

import com.shopx.common.enums.PaymentStatus;
import com.shopx.billing.kafka.PaymentEventPublisher;
import com.shopx.common.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SuccessPaymentProcessor implements PaymentProcessor {

    private final PaymentEventPublisher paymentEventPublisher;

    @Override
    public PaymentStatus getType() {
        return PaymentStatus.SUCCESS;
    }

    @Override
    public void process(PaymentEvent paymentEvent) {

       paymentEventPublisher.publish(paymentEvent);

    }
}