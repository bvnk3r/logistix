package org.logistix.common.messaging.delivery;

import java.math.BigDecimal;
import java.util.Set;

public record CreateDeliveryPayload(
        String orderNumber,
        String deliveryAddress,
        Set<DeliveryItemDto> items
) {
        public record DeliveryItemDto(
                int quantity,
                BigDecimal price,
                String productCode,
                String productName
        ) {}
}
