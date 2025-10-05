package org.logistix.clients.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.logistix.clients.model.ClientType;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Клиент")
public record ClientDto(
        UUID id,
        String name,
        String address,
        String phone,
        ClientType type,
        Instant createdDate,
        Instant lastModifiedDate
) {}
