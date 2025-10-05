package org.logistix.orders.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.orders.dto.client.ClientInfoDto;
import org.logistix.orders.dto.product.ProductInfoDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestService {
    private static final String CACHE_SERVICE_URL = "http://cache-service/api/v1/cache";
    private static final String CLIENT_CACHE_SERVICE_URL = CACHE_SERVICE_URL + "/client/%s";
    private static final String PRODUCT_CACHE_SERVICE_URL = CACHE_SERVICE_URL + "/product/%s";

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "clientService", fallbackMethod = "buildFallbackClientInfo")
    public ClientInfoDto getClientInfo(UUID clientId) {
        var url = String.format(CLIENT_CACHE_SERVICE_URL, clientId);
        return restTemplate.getForEntity(url, ClientInfoDto.class).getBody();
    }

    public ProductInfoDto getProductInfo(String productCode) {
        var url = String.format(PRODUCT_CACHE_SERVICE_URL, productCode);
        return restTemplate.getForEntity(url, ProductInfoDto.class).getBody();
    }

    @SuppressWarnings("unused")
    private ClientInfoDto buildFallbackClientInfo(UUID id, Throwable throwable) {
        log.info("Fallback called, clientId: {}, cause: {}", id, throwable.getMessage());
        return new ClientInfoDto("Fallback name", "Fallback address", "Fallback phone", "FL");
    }
}
