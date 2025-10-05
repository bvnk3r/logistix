package org.logistix.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "Товар")
public record ProductDto(
        UUID id,
        String code,
        String name,
        String unit,
        String place,
        BigDecimal amount,
        BigDecimal price,
        Instant createdDate,
        Instant lastModifiedDate
) {}
