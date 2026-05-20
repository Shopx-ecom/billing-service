package com.shopx.billing.processor;

import com.shopx.billing.kafka.PaymentEventPublisher;
import com.shopx.common.enums.PaymentStatus;
import com.shopx.common.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class FailedPaymentProcessor implements PaymentProcessor {

    private final PaymentEventPublisher paymentEventPublisher;

    @Override
    public PaymentStatus getType() {
        return PaymentStatus.FAILED;
    }

    @Override
    public void process(PaymentEvent paymentEvent) {

        paymentEventPublisher.publish(paymentEvent);

    }
}