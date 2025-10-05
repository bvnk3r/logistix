package org.logistix.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.logistix.common.messaging.delivery.DeliveryStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Schema(description = "Обновление доставки")
public record UpdateDeliveryDto(
        @NotNull
        String deliveryAddress,
        @NotNull
        Instant deliveryDate,
        @NotNull
        DeliveryStatus status,
        @NotNull
        @NotEmpty
        Set<DeliveryItemDto> items
) {
        @Schema(description = "Позиция в доставке")
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
