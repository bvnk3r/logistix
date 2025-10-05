package org.logistix.cache.dto;

import java.math.BigDecimal;

public record ProductInfoDto(
        String code,
        String name,
        BigDecimal amount,
        BigDecimal price
) {}
