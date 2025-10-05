package org.logistix.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Создание платежа")
public record CreatePaymentDto(
        @NotNull
        String orderNumber,
        @NotNull
        BigDecimal amount
) {}
