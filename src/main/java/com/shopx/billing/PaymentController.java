package com.shopx.billing;

import com.shopx.billing.core.Constants;
import com.shopx.common.enums.PaymentStatus;
import com.shopx.billing.dto.PaymentRequestDto;
import com.shopx.billing.dto.PaymentResponseDto;
import com.shopx.billing.kafka.PaymentEventPublisher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sameer Shaikh
 * @date 10-05-2026
 * @description
 */

@Slf4j
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

    @PostMapping("/update/status")
    public ResponseEntity<PaymentResponseDto> pay(
            @RequestParam Long orderId,
            @RequestParam String transactionId,
            @RequestParam PaymentStatus paymentStatus,
            HttpServletRequest request
    ){
        Long customerId =(Long) request.getAttribute(Constants.SESSION_ACTOR_ID);
        log.info("customerId : {}",customerId);
       return ResponseEntity.ok(
               PaymentMapper.toResponse( paymentservice.updateStatus(orderId,customerId,transactionId,paymentStatus))
       );
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
