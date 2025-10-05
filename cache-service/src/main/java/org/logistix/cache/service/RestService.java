package org.logistix.cache.service;

import lombok.RequiredArgsConstructor;
import org.logistix.cache.dto.ClientInfoDto;
import org.logistix.cache.dto.ProductInfoDto;
import org.logistix.cache.exception.ServiceUnavailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestService {
    private static final String CLIENT_INFO_URL = "http://client-service/api/v1/client/%s/info";
    private static final String PRODUCT_INFO_URL = "http://product-service/api/v1/product/%s/info";

    private final RestTemplate restTemplate;

    public ClientInfoDto getClientInfo(UUID clientId) {
        var url = String.format(CLIENT_INFO_URL, clientId);
        ResponseEntity<ClientInfoDto> response = restTemplate.getForEntity(url, ClientInfoDto.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new ServiceUnavailableException("Client service is not available");
        }
    }

    public ProductInfoDto getProductInfo(String productCode) {
        var url = String.format(PRODUCT_INFO_URL, productCode);
        ResponseEntity<ProductInfoDto> response = restTemplate.getForEntity(url, ProductInfoDto.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new ServiceUnavailableException("Product service is not available");
        }
    }
}
