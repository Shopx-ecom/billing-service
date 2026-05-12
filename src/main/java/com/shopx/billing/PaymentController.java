package com.shopx.billing;

import com.shopx.billing.dto.PaymentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sameer Shaikh
 * @date 10-05-2026
 * @description
 */

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentEventPublisher paymentEventPublisher;
    private final Paymentservice paymentservice;

    @PostMapping
    public String test(){
        return paymentEventPublisher.test();
    }

    @PostMapping("/pay")
    public void pay(

    ){

    }

    @PostMapping("/create")
    public ResponseEntity<Payment> create(
            @RequestBody PaymentRequestDto dto
    ){

        return ResponseEntity.ok(paymentservice.create(
                PaymentMapper.toEntity(dto,"1")
        ));
    }

}
