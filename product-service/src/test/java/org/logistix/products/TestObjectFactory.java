package org.logistix.products;

import org.logistix.products.dto.CreateProductDto;
import org.logistix.products.dto.UpdateProductDto;
import org.logistix.products.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TestObjectFactory {

    public CreateProductDto createProductDto(String code) {
        return new CreateProductDto(
                code,
                "name",
                "pcs",
                "place",
                BigDecimal.ONE,
                new BigDecimal(10)
        );
    }

    public UpdateProductDto updateProductDto() {
        return new UpdateProductDto(
                "name1",
                "oz",
                "place1",
                BigDecimal.TWO,
                new BigDecimal(5)
        );
    }

    public Product newProductEntity() {
        return Product.builder()
                .code("code")
                .name("name")
                .unit("pcs")
                .place("place")
                .amount(BigDecimal.ONE)
                .price(new BigDecimal(10))
                .build();
    }
}
