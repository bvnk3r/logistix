package org.logistix.payment.mapper;

import org.logistix.common.messaging.payment.CreatePaymentPayload;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import org.logistix.payment.dto.CreatePaymentDto;
import org.logistix.payment.dto.PaymentDto;
import org.logistix.payment.dto.UpdatePaymentDto;
import org.logistix.payment.model.entity.Payment;

@Mapper(componentModel = SPRING)
public interface PaymentMapper {

    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "paymentDate", expression = "java(Instant.now())")
    Payment fromCreateDto(CreatePaymentDto dto);

    @Mapping(target = "status", constant = "COMPLETED")
    @Mapping(target = "paymentDate", expression = "java(Instant.now())")
    Payment fromCreatePayload(CreatePaymentPayload dto);

    PaymentDto toDto(Payment entity);

    void update(UpdatePaymentDto dto, @MappingTarget Payment entity);
}