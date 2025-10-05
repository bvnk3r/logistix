package org.logistix.common.messaging.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductInfoPayload(
        UUID id,
        String code,
        String name,
        String unit,
        BigDecimal amount,
        BigDecimal price
) {}
