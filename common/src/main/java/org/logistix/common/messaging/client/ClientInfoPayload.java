package org.logistix.common.messaging.client;

import java.util.UUID;

public record ClientInfoPayload(

        UUID id,
        String name,
        String address,
        String phone,
        String type
) {}
