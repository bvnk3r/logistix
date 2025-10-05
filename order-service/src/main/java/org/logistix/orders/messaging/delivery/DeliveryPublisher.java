package org.logistix.orders.messaging.delivery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryPublisher {

    private final StreamBridge streamBridge;

    public void createDelivery(CreateDeliveryPayload payload) {
       log.debug("Sending Kafka message for order id: {}", payload.orderNumber());
       streamBridge.send("delivery-out-0", MessageBuilder.withPayload(payload).build());
    }
}