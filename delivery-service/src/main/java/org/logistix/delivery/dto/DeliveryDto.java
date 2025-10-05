package org.logistix.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.logistix.common.messaging.delivery.DeliveryStatus;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Доставка")
public record DeliveryDto(
        UUID id,
        String orderNumber,
        String deliveryAddress,
        DeliveryStatus status,
        Set<DeliveryItemDto> items,
        Instant createdDate,
        Instant lastModifiedDate
) {}
