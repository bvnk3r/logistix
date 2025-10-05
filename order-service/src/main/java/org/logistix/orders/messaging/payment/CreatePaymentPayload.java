package org.logistix.orders.messaging.payment;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatePaymentPayload(
        @NotNull
        String orderNumber,
        @NotNull
        BigDecimal amount
) {}