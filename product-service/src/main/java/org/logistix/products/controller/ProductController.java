package org.logistix.products.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.logistix.products.dto.ProductInfoDto;
import org.logistix.products.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Получить информацию о товаре по коду", tags = "product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Информация о товаре"),
                    @ApiResponse(responseCode = "404", description = "Товар не найден")
            })
    @GetMapping("/{code}/info")
    public ProductInfoDto getProductInfoByCode(@PathVariable String code) {
        return productService.getProductInfo(code);
    }
}
