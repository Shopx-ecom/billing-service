package com.shopx.billing.processor;

import com.shopx.billing.Payment;
import com.shopx.billing.Paymentservice;
import com.shopx.common.enums.PaymentStatus;
import com.shopx.common.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
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


    @Override
    public PaymentStatus getType() {
        return PaymentStatus.PENDING;
    }

    @Override
    public void process(PaymentEvent paymentEvent) {


    }
}
