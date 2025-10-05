package org.logistix.clients.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.common.messaging.client.ClientInfoPayload;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientInfoPublisher {

    private final StreamBridge streamBridge;

    public void publishClientUpdate(ClientInfoPayload payload){
       log.debug("Sending Kafka message for client id: {}", payload.id());
       streamBridge.send("output-out-0", MessageBuilder.withPayload(payload).build());
    }
}