package org.logistix.delivery.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.common.messaging.delivery.DeliveryStatusPayload;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderDeliveryStatusPublisher {

    private final StreamBridge streamBridge;

    public void publishOrderDeliveryStatus(DeliveryStatusPayload payload) {
       log.debug("Sending Kafka message for order: {}", payload.orderNumber());
       streamBridge.send("output-out-0", MessageBuilder.withPayload(payload).build());
    }
}