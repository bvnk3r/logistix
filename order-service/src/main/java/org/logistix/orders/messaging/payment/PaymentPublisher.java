package org.logistix.orders.messaging.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentPublisher {

    private final StreamBridge streamBridge;

    public void createPayment(CreatePaymentPayload payload) {
       log.debug("Sending Kafka message for order id: {}", payload.orderNumber());
       streamBridge.send("payment-out-0", MessageBuilder.withPayload(payload).build());
    }
}