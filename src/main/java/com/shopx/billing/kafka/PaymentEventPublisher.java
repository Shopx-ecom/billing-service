package com.shopx.billing.kafka;

import com.shopx.billing.Payment;
import com.shopx.common.enums.PaymentMethod;
import com.shopx.common.enums.PaymentStatus;
import com.shopx.common.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Slf4j
@Component
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Value("${kafka.topic.payment-success:payment.success}")
    private String paymentSuccessTopic;

    @Value("${kafka.topic.payment-failed:payment.failed}")
    private String paymentFailedTopic;

 /*   public void publish(Payment payment) {
        PaymentEvent event = PaymentEvent.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .customerId(payment.getCustomerId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
//                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .failureReason(payment.getFailureReason())
                .occurredAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            event.setEventType("payment.success");
            kafkaTemplate.send(paymentSuccessTopic, String.valueOf(payment.getOrderId()), event);
            log.info("Published payment.success event for orderId: {}", payment.getOrderId());
        } else if (payment.getStatus() == PaymentStatus.FAILED) {
            event.setEventType("payment.failed");
            kafkaTemplate.send(paymentFailedTopic, String.valueOf(payment.getOrderId()), event);
            log.info("Published payment.failed event for orderId: {}", payment.getOrderId());
        }
    }*/

    public String test(){
        kafkaTemplate.send("testing","test-1",
                PaymentEvent.builder()
                        .paymentId(100L)
                        .orderId(121L)
                        .amount(1000.0)
                        .customerId(10L)
                        .status(PaymentStatus.PENDING)
                        .currency("INR")
                        .paymentMethod(PaymentMethod.UPI)
                        .build());
        return "sent";
    }

    public void publish(PaymentEvent event){
        kafkaTemplate.send(event.getTopic(),"payment-group",event);
        log.info("payment {} event sent",event.getStatus());
    }

}