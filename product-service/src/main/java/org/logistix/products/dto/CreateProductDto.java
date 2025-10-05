package org.logistix.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Создание товара")
public record CreateProductDto(
        @NotNull
        String code,
        @NotNull
        String name,
        @NotNull
        String unit,
        @NotNull
        String place,
        @NotNull
        BigDecimal amount,
        @NotNull
        BigDecimal price
) {}
