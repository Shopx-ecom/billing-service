package com.shopx.billing.processor;

import com.shopx.billing.Payment;
import com.shopx.billing.PaymentEvent;
import com.shopx.billing.core.enums.PaymentStatus;

public interface PaymentProcessor {

    PaymentStatus getType();

    void process(PaymentEvent paymentEvent);
}