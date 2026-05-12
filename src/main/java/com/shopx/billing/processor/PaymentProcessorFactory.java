package com.shopx.billing.processor;

import com.shopx.billing.core.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentProcessorFactory {

    private final Map<PaymentStatus, PaymentProcessor> processors;

    public PaymentProcessorFactory(List<PaymentProcessor> processorList) {

        this.processors = processorList.stream()
                .collect(Collectors.toMap(
                        PaymentProcessor::getType,
                        Function.identity()
                ));
    }

    public PaymentProcessor getProcessor(PaymentStatus status) {
        return processors.get(status);
    }
}