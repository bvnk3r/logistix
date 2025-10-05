package org.logistix.clients.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.logistix.clients.model.ClientType;

@Schema(description = "Клиент")
public record ClientInfoDto(
        String name,
        String address,
        String phone,
        ClientType type
) {}
