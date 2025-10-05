package org.logistix.cache.scheduled;

import lombok.RequiredArgsConstructor;
import org.logistix.cache.repository.ClientInfoRepository;
import org.logistix.cache.repository.ProductInfoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheScheduler {

    private final ClientInfoRepository clientInfoRepository;
    private final ProductInfoRepository productInfoRepository;

    @Scheduled(cron = "${evict-cache-cron}")
    public void evictCache() {
        clientInfoRepository.deleteAll();
        productInfoRepository.deleteAll();
    }
}
