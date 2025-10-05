package org.logistix.payment.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.common.messaging.payment.PaymentStatusPayload;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentStatusPublisher {

    private final StreamBridge streamBridge;

    public void publishOrderPaymentStatus(PaymentStatusPayload payload) {
       log.debug("Sending Kafka message for order id: {}", payload.orderNumber());
       streamBridge.send("output-out-0", MessageBuilder.withPayload(payload).build());
    }
}