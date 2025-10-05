package org.logistix.common.messaging.delivery;

public record DeliveryStatusPayload(
        String orderNumber,
        DeliveryStatus status
) {}
