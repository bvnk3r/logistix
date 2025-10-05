package org.logistix.orders.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import org.logistix.orders.model.OrderStatus;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Заказ")
public record OrderDto(
        UUID id,
        String orderNumber,
        String deliveryAddress,
        UUID clientId,
        OrderStatus status,
        Set<OrderItemDto> items,
        Instant createdDate,
        Instant lastModifiedDate
) {}
