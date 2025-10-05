package org.logistix.common.messaging.payment;

public record PaymentStatusPayload(
        String orderNumber,
        PaymentStatus status
) {}
