package org.logistix.orders.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "Позиция в заказе")
public record OrderItemDto(
        UUID id,
        int quantity,
        BigDecimal price,
        String productCode,
        String productName,
        Instant createdDate,
        Instant lastModifiedDate
) {}
