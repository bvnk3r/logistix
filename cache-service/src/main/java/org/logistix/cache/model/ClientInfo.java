package org.logistix.cache.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash
public record ClientInfo(

        @Id
        UUID id,
        String name,
        String address,
        String phone,
        String type
) {}
