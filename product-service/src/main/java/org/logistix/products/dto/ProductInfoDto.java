package org.logistix.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Товар")
public record ProductInfoDto(
        String code,
        String name,
        String unit,
        String place,
        BigDecimal amount,
        BigDecimal price
) {}
