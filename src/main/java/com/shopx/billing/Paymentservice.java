package com.shopx.billing;

import com.shopx.billing.core.DefaultFilter;
import com.shopx.billing.core.FindResourceOption;
import com.shopx.billing.core.ResourceService;
import com.shopx.common.enums.PaymentMethod;
import com.shopx.common.enums.PaymentStatus;
import com.shopx.billing.dto.PaymentRequestDto;
import com.shopx.billing.dto.PaymentUpdateDto;
import com.shopx.billing.exception.NotFoundException;
import com.shopx.billing.kafka.PaymentEventPublisher;
import com.shopx.billing.processor.PaymentProcessor;
import com.shopx.billing.processor.PaymentProcessorFactory;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sameer Shaikh
 * @date 10-05-2026
 * @description
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class Paymentservice extends ResourceService<Payment> {

    private final PaymentRepository paymentRepository;

    @Lazy
    private final PaymentProcessorFactory factory;

    @Override
    protected Class<Payment> getEntityType() {
        return Payment.class;
    }

    @Override
    protected JpaRepository<Payment, Long> getRepository() {
        return paymentRepository;
    }

    @Override
    protected JpaSpecificationExecutor<Payment> getSpecificationExecutorRepository() {
        return paymentRepository;
    }

    @Override
    protected String getResourceName() {
        return "payment";
    }

    @Override
    protected Specification<Payment> getPassedFilters(Object filters, DefaultFilter defaultFilter) {
        Specification<Payment> parentSpec = super.getPassedFilters(filters, defaultFilter);
        PaymentFilter filter = (PaymentFilter) filters;

        Specification<Payment> childSpec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter.getId() != null)
                predicate = cb.and(predicate, cb.equal(root.get("id"), filter.getId()));

            if (filter.getIds() != null && !filter.getIds().isEmpty())
                predicate = cb.and(predicate, root.get("id").in(filter.getIds()));

            if (filter.getOrderId() != null)
                predicate = cb.and(predicate, cb.equal(root.get("orderId"), filter.getOrderId()));

            if (filter.getCustomerId() != null)
                predicate = cb.and(predicate, cb.equal(root.get("customerId"), filter.getCustomerId()));

            if (filter.getAmount() != null)
                predicate = cb.and(predicate, cb.equal(root.get("amount"), filter.getAmount()));

            if (filter.getStatus() != null)
                predicate = cb.and(predicate, cb.equal(root.get("status"), filter.getStatus()));

            if (filter.getPaymentMethod() != null)
                predicate = cb.and(predicate, cb.equal(root.get("paymentMethod"), filter.getPaymentMethod()));

            if (filter.getTransactionId() != null)
                predicate = cb.and(predicate,
                        cb.equal(root.get("transactionId"), filter.getTransactionId()));

            if (filter.getCurrency() != null)
                predicate = cb.and(predicate,
                        cb.equal(cb.lower(root.get("currency")), filter.getCurrency().toLowerCase()));

            if (filter.getSearch() != null) {
                String pattern = "%" + filter.getSearch().toLowerCase() + "%";
                predicate = cb.and(predicate,
                        cb.or(
                                cb.like(cb.lower(root.get("transactionId")), pattern),
                                cb.like(cb.lower(root.get("currency")), pattern),
                                cb.like(cb.lower(root.get("failureReason")), pattern)
                        ));
            }

            return predicate;
        };

        return Specification.where(parentSpec).and(childSpec);
    }

   /* public void pay(Long paymentId, PaymentStatus paymentStatus) {

        Payment payment = updatePayment(
                paymentId,
                PaymentUpdateDto.builder()
                        .paymentStatus(paymentStatus).build()
        );

        PaymentProcessor processor =
                factory.getProcessor(paymentStatus);

        processor.process(null);
    }*/

    public Payment create(Payment payment){
        log.info("Payment created : {}",payment.toString());
        return this.create(payment, Map.of());
    }

    public Payment updatePayment(Long paymentId, PaymentUpdateDto dto){

        Payment payment = findResource(paymentId);
        if(payment==null) throw new NotFoundException("Payment record not found with id : "+paymentId);

        Payment updated = this.update(paymentId,PaymentMapper.toMap(dto), Optional.empty());
        return updated;
    }

    public Payment updateStatus(
            Long orderId,
            Long customerId,
            String transactionId,
            PaymentStatus paymentStatus
    ){

        List<Payment> payments  = findResources(
                PaymentFilter.builder()
                        .orderId(orderId)
                        .customerId(customerId)
                        .build(),
                FindResourceOption.builder().build(),
                DefaultFilter.builder().build()
        ).getData();

        if(payments==null || payments.isEmpty())
            throw new NotFoundException("Payment failed.");

        Payment payment = payments.getFirst();

        if(!payment.getStatus().equals(PaymentStatus.PENDING))
            throw new NotFoundException("Payment not found.");

        Payment updated = this.update(payment.getId(),Map.of("status",paymentStatus),Optional.empty());

        PaymentProcessor processor = factory.getProcessor(paymentStatus);

        processor.process(
                com.shopx.common.event.PaymentEvent.builder()
                        .orderId(orderId)
                        .customerId(customerId)
                        .paymentMethod(payment.getPaymentMethod())
                        .status(paymentStatus)
                        .topic("payment-events")
                        .paymentId(payment.getId())
                        .transactionId(transactionId)
                        .amount(payment.getAmount())
                        .currency("INR")
                        .build()
        );

        return updated;
    }

}
