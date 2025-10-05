package org.logistix.products.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.products.dto.ProductInfoDto;
import org.logistix.products.exception.EntityNotFoundException;
import org.logistix.products.mapper.ProductMapper;
import org.logistix.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductInfoDto getProductInfo(String code) {
        var product = productRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Product not found, code=" + code));
        return productMapper.toInfoDto(product);
    }
}