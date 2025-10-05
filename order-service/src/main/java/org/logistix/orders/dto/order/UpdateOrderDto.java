package org.logistix.orders.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.logistix.orders.model.OrderStatus;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Обновление заказа")
public record UpdateOrderDto(
        @NotNull
        String deliveryAddress,
        @NotNull
        UUID clientId,
        @NotNull
        OrderStatus status,
        @NotNull
        @NotEmpty
        Set<OrderItemDto> items
) {
        @Schema(description = "Позиция в заказе")
        public record OrderItemDto(
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
