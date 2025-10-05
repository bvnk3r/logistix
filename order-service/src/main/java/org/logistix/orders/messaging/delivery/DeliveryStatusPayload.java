package org.logistix.orders.messaging.delivery;

public record DeliveryStatusPayload(
        String orderNumber,
        DeliveryStatus status
) {}
