package org.logistix.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.logistix.common.messaging.payment.PaymentStatus;

import java.math.BigDecimal;

@Schema(description = "Обновление платежа")
public record UpdatePaymentDto(
        BigDecimal amount,
        PaymentStatus status
) {}
