package org.logistix.clients.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.logistix.clients.model.ClientType;

@Schema(description = "Обновление клиента")
public record UpdateClientDto(
        String name,
        String address,
        String phone,
        ClientType type
) {}
