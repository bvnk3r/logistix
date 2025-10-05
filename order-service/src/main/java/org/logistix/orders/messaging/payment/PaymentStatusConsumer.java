package org.logistix.orders.messaging.payment;

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
public class PaymentStatusConsumer {

    private final OrderService orderService;

    @Bean
    public Consumer<PaymentStatusPayload> updateOrderPaymentStatusSink() {
        return payload -> {
            log.info("Received update for order number {}", payload.orderNumber());
            if (payload.status().equals(PaymentStatus.COMPLETED)) {
                orderService.updateOrderStatus(payload.orderNumber(), OrderStatus.COMPLETED);
                orderService.deliverOrder(payload.orderNumber());
            }
        };
    }
}
