package org.logistix.orders.messaging.delivery;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public record CreateDeliveryPayload(
        @NotNull
        String orderNumber,
        @NotNull
        String deliveryAddress,
        @NotNull
        @NotEmpty
        Set<DeliveryItemDto> items
) {
        public record DeliveryItemDto(
                @NotNull
                int quantity,
                @NotNull
                BigDecimal price,
                @NotNull
                String productCode,
                @NotNull
                String productName
        ) {}
}
