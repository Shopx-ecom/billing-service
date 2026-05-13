package com.shopx.billing;

import com.shopx.billing.processor.PaymentProcessor;
import com.shopx.billing.processor.PaymentProcessorFactory;
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

    private final PaymentProcessorFactory paymentProcessorFactory;

    @KafkaListener(
            topics = "testing",
            groupId = "payment-group"
    )
    public void test(PaymentEvent event){

        PaymentProcessor processor = paymentProcessorFactory.getProcessor(event.getStatus());
        processor.process(event);
        log.info("Event consumed : {}",event);
    }

    @KafkaListener(
            topics = "order-events",
            groupId = "payment-group"
    )
    public void consumeOrderEvent(OrderEvent event){

        PaymentProcessor processor = paymentProcessorFactory.getProcessor(event.getStatus());
        processor.process(event);
        log.info("Event consumed : {}",event);
    }

}
