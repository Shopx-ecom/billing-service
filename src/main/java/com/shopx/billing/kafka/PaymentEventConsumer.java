package com.shopx.billing.kafka;

import com.shopx.billing.Payment;
import com.shopx.billing.Paymentservice;
import com.shopx.billing.exception.NotFoundException;
import com.shopx.billing.processor.PaymentProcessor;
import com.shopx.billing.processor.PaymentProcessorFactory;
import com.shopx.common.enums.PaymentMethod;
import com.shopx.common.enums.PaymentStatus;
import com.shopx.common.event.OrderEvent;
import com.shopx.common.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Sameer Shaikh
 * @date 11-05-2026
 * @description
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentEventConsumer {

    private final Paymentservice paymentService;
/*
    @KafkaListener(
            topics = "testing",
            groupId = "payment-group"
    )
    public void test(PaymentEvent event){

        PaymentProcessor processor = paymentProcessorFactory.getProcessor(event.getStatus());
        processor.process(event);
        log.info("Event consumed : {}",event);
    }*/

    @KafkaListener(
            topics = "order-events",
            groupId = "payment-group"
    )
    public void consumeOrderEvent(OrderEvent orderEvent){

        if(orderEvent.getEventType()!=null)
            return;

        if(orderEvent.getEventType().equals("order-created")) {
            Payment payment = Payment.builder()
                    .orderId(orderEvent.getOrderId())
                    .customerId(orderEvent.getCustomerId())
                    .status(PaymentStatus.PENDING)
                    .amount(orderEvent.getTotalAmount())
                    .currency("INR")
                    .paymentMethod(null)
                    .build();

            Payment paymentStored = paymentService.create(payment);
            log.info("Event consumed : {}",orderEvent);

        }else if(orderEvent.getEventType().equals("order-cancelled")){
            paymentService.updateStatus(
                    orderEvent.getOrderId(),
                    orderEvent.getCustomerId(),
                    null,
                    PaymentStatus.CANCELLED
            );
        }

    }

}
