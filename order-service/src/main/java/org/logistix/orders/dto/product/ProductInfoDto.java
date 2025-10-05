package org.logistix.orders.dto.product;

import java.math.BigDecimal;

public record ProductInfoDto(
        String code,
        String name,
        BigDecimal amount,
        BigDecimal price
) {}
