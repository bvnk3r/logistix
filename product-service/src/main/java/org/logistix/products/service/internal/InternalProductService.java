package org.logistix.products.service.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.common.messaging.product.ProductInfoPayload;
import org.logistix.products.dto.CreateProductDto;
import org.logistix.products.dto.ProductDto;
import org.logistix.products.dto.UpdateProductDto;
import org.logistix.products.exception.EntityNotFoundException;
import org.logistix.products.mapper.ProductMapper;
import org.logistix.products.messaging.ProductInfoPublisher;
import org.logistix.products.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InternalProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductInfoPublisher productInfoPublisher;

    public Page<ProductDto> list(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    public ProductDto getById(UUID id) {
        return productMapper.toDto(productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found, id=" + id)));
    }

    public ProductDto create(CreateProductDto createProductDto) {
        var product = productMapper.fromCreateDto(createProductDto);
        return productMapper.toDto(productRepository.save(product));
    }

    public ProductDto update(UUID productId, UpdateProductDto productDto) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found, id=" + productId));
        productMapper.update(productDto, product);
        var result = productRepository.save(product);
        try {
            productInfoPublisher.publishProductUpdate(new ProductInfoPayload(
                    result.getId(),
                    result.getCode(),
                    result.getName(),
                    result.getUnit(),
                    result.getAmount(),
                    result.getPrice()
            ));
        } catch (Exception e) {
            log.error("Failed to send message, cause: {}", e.getMessage());
        }
        return productMapper.toDto(result);
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
