package org.logistix.cache.dto;

public record ClientInfoDto(
        String name,
        String address,
        String phone,
        String type
) {}
