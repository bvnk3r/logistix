package org.logistix.products.controller.internal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.logistix.products.dto.CreateProductDto;
import org.logistix.products.dto.ProductDto;
import org.logistix.products.dto.UpdateProductDto;
import org.logistix.products.service.internal.InternalProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal/api/v1/product")
@RequiredArgsConstructor
public class InternalProductController {

    private final InternalProductService internalProductService;

    @Operation(summary = "Получить список товаров", tags = "internal-product", responses = {
            @ApiResponse(responseCode = "200", description = "Список товаров")
    })
    @GetMapping
    public Page<ProductDto> list(
            @SortDefault(sort = "lastModifiedDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return internalProductService.list(pageable);
    }

    @Operation(summary = "Получить товар", tags = "internal-product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Товар"),
                    @ApiResponse(responseCode = "404", description = "Товар не найден")
            })
    @GetMapping("/{productId}")
    public ProductDto getById(@PathVariable UUID productId) {
        return internalProductService.getById(productId);
    }

    @Operation(summary = "Создать товар", tags = "internal-product", responses = {
            @ApiResponse(responseCode = "200", description = "Созданный товар"),
            @ApiResponse(responseCode = "400", description = "Ошибочный запрос")
    })
    @PostMapping
    public ProductDto create(@Valid @RequestBody CreateProductDto createProductDto) {
        return internalProductService.create(createProductDto);
    }

    @Operation(summary = "Обновить товар", tags = "internal-product", responses = {
            @ApiResponse(responseCode = "200", description = "Обновленный товар"),
            @ApiResponse(responseCode = "404", description = "Товар не найден")
    })
    @PutMapping("/{productId}")
    public ProductDto update(
            @PathVariable UUID productId, @Valid @RequestBody UpdateProductDto updateProductDto) {
        return internalProductService.update(productId, updateProductDto);
    }

    @Operation(summary = "Удалить товар", tags = "internal-product", responses = {
            @ApiResponse(responseCode = "200", description = "Товар удален"),
            @ApiResponse(responseCode = "404", description = "Товар не найден")
    })
    @DeleteMapping("/{productId}")
    public void delete(@PathVariable UUID productId) {
        internalProductService.delete(productId);
    }
}
