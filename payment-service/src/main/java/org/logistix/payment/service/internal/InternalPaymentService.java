package org.logistix.payment.service.internal;

import lombok.RequiredArgsConstructor;

import org.logistix.payment.dto.CreatePaymentDto;
import org.logistix.payment.dto.PaymentDto;
import org.logistix.payment.dto.UpdatePaymentDto;
import org.logistix.payment.exception.EntityNotFoundException;
import org.logistix.payment.mapper.PaymentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class InternalPaymentService {

    private final org.logistix.payment.repository.PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public Page<PaymentDto> list(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(paymentMapper::toDto);
    }

    public PaymentDto getById(UUID id) {
        return paymentMapper.toDto(paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found, id=" + id)));
    }

    public PaymentDto create(CreatePaymentDto createPaymentDto) {
        return paymentMapper.toDto(paymentRepository.save(paymentMapper.fromCreateDto(createPaymentDto)));
    }

    public PaymentDto update(UUID paymentId, UpdatePaymentDto paymentDto) {
        var payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found, id=" + paymentId));
        paymentMapper.update(paymentDto, payment);
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    public void delete(UUID id) {
        paymentRepository.deleteById(id);
    }
}
