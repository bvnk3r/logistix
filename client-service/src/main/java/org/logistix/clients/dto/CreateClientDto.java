package org.logistix.clients.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.logistix.clients.model.ClientType;

@Schema(description = "Создание клиента")
public record CreateClientDto(
        String name,
        String address,
        String phone,
        ClientType type
) {}
