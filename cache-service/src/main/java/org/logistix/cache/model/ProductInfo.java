package org.logistix.cache.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.UUID;

@RedisHash
public record ProductInfo(

        @Id
        UUID id,
        String code,
        String name,
        String unit,
        BigDecimal amount,
        BigDecimal price
) {}
