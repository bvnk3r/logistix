package org.logistix.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.logistix.common.messaging.payment.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "Платеж")
public record PaymentDto(
        UUID id,
        String orderNumber,
        BigDecimal amount,
        Instant paymentDate,
        PaymentStatus status,
        Instant createdDate,
        Instant lastModifiedDate
) {}
