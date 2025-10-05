package org.logistix.orders.messaging.payment;

public record PaymentStatusPayload(
        String orderNumber,
        PaymentStatus status
) {}
