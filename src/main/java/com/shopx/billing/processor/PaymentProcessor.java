package com.shopx.billing.processor;

import com.shopx.common.enums.PaymentStatus;
import com.shopx.common.event.PaymentEvent;

public interface PaymentProcessor {

    PaymentStatus getType();

    void process(PaymentEvent paymentEvent);
}