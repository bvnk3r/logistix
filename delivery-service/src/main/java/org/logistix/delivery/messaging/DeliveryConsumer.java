package org.logistix.delivery.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.common.messaging.delivery.CreateDeliveryPayload;
import org.logistix.delivery.service.DeliveryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeliveryConsumer {

    private final DeliveryService deliveryService;

    @Bean
    public Consumer<CreateDeliveryPayload> createDeliverySink() {
        return payload -> {
            log.info("Received new delivery for order number {}", payload.orderNumber());

            deliveryService.create(payload);
        };
    }
}
