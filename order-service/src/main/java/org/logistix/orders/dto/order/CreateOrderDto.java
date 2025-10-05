package org.logistix.orders.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Создание заказа")
public record CreateOrderDto(
        @NotNull
        String orderNumber,
        @NotNull
        String deliveryAddress,
        @NotNull
        UUID clientId,
        @NotNull
        @NotEmpty
        Set<OrderItemDto> items
) {
        @Schema(description = "Позиция в заказе")
        public record OrderItemDto(
                @NotNull
                int quantity,
                @NotNull
                String productCode
        ) {}
}
