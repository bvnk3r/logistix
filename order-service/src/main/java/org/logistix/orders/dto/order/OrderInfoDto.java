package org.logistix.orders.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import org.logistix.orders.model.OrderStatus;

import java.util.Set;

@Schema(description = "Заказ")
public record OrderInfoDto(
        String orderNumber,
        String deliveryAddress,
        String clientPhone,
        String clientName,
        OrderStatus status,
        Set<OrderItemDto> items
) {}
