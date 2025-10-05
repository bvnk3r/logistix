package org.logistix.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

@Schema(description = "Создание доставки")
public record CreateDeliveryDto(
        @NotNull
        String orderNumber,
        @NotNull
        String deliveryAddress,
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
