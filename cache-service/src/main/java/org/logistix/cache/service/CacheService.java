package org.logistix.cache.service;

import lombok.RequiredArgsConstructor;
import org.logistix.cache.mapper.CacheMapper;
import org.logistix.cache.model.ClientInfo;
import org.logistix.cache.model.ProductInfo;
import org.logistix.cache.repository.ClientInfoRepository;
import org.logistix.cache.repository.ProductInfoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final ClientInfoRepository clientInfoRepository;
    private final ProductInfoRepository productInfoRepository;

    private final RestService restService;
    private final CacheMapper cacheMapper;

    public ClientInfo getClientInfo(UUID id) {
        var clientInfo = clientInfoRepository.findById(id).orElse(null);
        if (clientInfo == null) {
            clientInfo = cacheMapper.fromClientInfoDto(restService.getClientInfo(id));
            clientInfoRepository.save(clientInfo);
        }
        return clientInfo;
    }

    public ProductInfo getProductInfo(String code) {
        var productInfo = productInfoRepository.findByCode(code).orElse(null);
        if (productInfo == null) {
            productInfo = cacheMapper.fromProductInfoDto(restService.getProductInfo(code));
            productInfoRepository.save(productInfo);
        }
        return productInfo;
    }
}
