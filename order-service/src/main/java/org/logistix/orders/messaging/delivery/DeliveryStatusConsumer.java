package org.logistix.orders.messaging.delivery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.orders.model.OrderStatus;
import org.logistix.orders.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeliveryStatusConsumer {

    private final OrderService orderService;

    @Bean
    public Consumer<DeliveryStatusPayload> updateOrderDeliveryStatusSink() {
        return payload -> {
            log.info("Received update for order number {}", payload.orderNumber());
            orderService.updateOrderStatus(payload.orderNumber(), OrderStatus.valueOf(payload.status().name()));
        };
    }
}
