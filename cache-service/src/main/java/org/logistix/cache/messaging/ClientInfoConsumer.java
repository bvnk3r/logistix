package org.logistix.cache.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.cache.mapper.CacheMapper;
import org.logistix.cache.repository.ClientInfoRepository;
import org.logistix.common.messaging.client.ClientInfoPayload;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ClientInfoConsumer {

    private final ClientInfoRepository clientInfoRepository;
    private final CacheMapper cacheMapper;

    @Bean
    public Consumer<ClientInfoPayload> updateClientInfoSink() {
        return payload -> {
            log.info("Received update for client id {}", payload.id());
            clientInfoRepository.save(cacheMapper.fromClientInfoPayload(payload));
        };
    }
}
