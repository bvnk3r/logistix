package org.logistix.products.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.common.messaging.product.ProductInfoPayload;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductInfoPublisher {

    private final StreamBridge streamBridge;

    public void publishProductUpdate(ProductInfoPayload payload){
       log.debug("Sending message for product id: {}", payload.id());
       streamBridge.send("output-out-0", MessageBuilder.withPayload(payload).build());
    }
}