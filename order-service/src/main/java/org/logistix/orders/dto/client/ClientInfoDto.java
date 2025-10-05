package org.logistix.orders.dto.client;

public record ClientInfoDto(
        String name,
        String address,
        String phone,
        String type
) {}
