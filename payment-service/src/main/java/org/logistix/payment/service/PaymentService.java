package org.logistix.payment.service;

import lombok.RequiredArgsConstructor;
import org.logistix.common.messaging.payment.CreatePaymentPayload;
import org.logistix.common.messaging.payment.PaymentStatus;
import org.logistix.common.messaging.payment.PaymentStatusPayload;
import org.logistix.payment.dto.PaymentDto;
import org.logistix.payment.mapper.PaymentMapper;
import org.logistix.payment.messaging.OrderPaymentStatusPublisher;
import org.logistix.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderPaymentStatusPublisher orderPaymentStatusPublisher;

    public PaymentDto create(CreatePaymentPayload createPaymentDto) {
        var payment = paymentMapper.fromCreatePayload(createPaymentDto);
        orderPaymentStatusPublisher.publishOrderPaymentStatus(
                new PaymentStatusPayload(createPaymentDto.orderNumber(), PaymentStatus.COMPLETED));
        return paymentMapper.toDto(paymentRepository.save(payment));
    }
}
