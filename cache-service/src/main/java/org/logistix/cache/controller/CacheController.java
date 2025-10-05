package org.logistix.cache.controller;

import lombok.RequiredArgsConstructor;
import org.logistix.cache.dto.ClientInfoDto;
import org.logistix.cache.dto.ProductInfoDto;
import org.logistix.cache.mapper.CacheMapper;
import org.logistix.cache.service.CacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cache")
public class CacheController {

    private final CacheService cacheService;
    private final CacheMapper cacheMapper;

    @GetMapping("/client/{clientId}")
    public ClientInfoDto getClientInfo(@PathVariable UUID clientId) {

        return cacheMapper.toClientInfoDto(cacheService.getClientInfo(clientId));
    }

    @GetMapping("/product/{productCode}")
    public ProductInfoDto getProductInfo(@PathVariable String productCode) {
        return cacheMapper.toProductInfoDto(cacheService.getProductInfo(productCode));
    }
}
