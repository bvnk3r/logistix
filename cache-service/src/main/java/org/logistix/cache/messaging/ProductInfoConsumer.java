package org.logistix.cache.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.cache.mapper.CacheMapper;
import org.logistix.cache.repository.ProductInfoRepository;
import org.logistix.common.messaging.product.ProductInfoPayload;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductInfoConsumer {

    private final ProductInfoRepository productInfoRepository;
    private final CacheMapper cacheMapper;

    @Bean
    public Consumer<ProductInfoPayload> updateProductInfoSink() {
        return payload -> {
            log.info("Received update for product id {}", payload.id());
            productInfoRepository.save(cacheMapper.fromProductInfoPayload(payload));
        };
    }
}
