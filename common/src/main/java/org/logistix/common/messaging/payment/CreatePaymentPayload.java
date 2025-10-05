package org.logistix.common.messaging.payment;

import java.math.BigDecimal;

public record CreatePaymentPayload(
        String orderNumber,
        BigDecimal amount
) {}