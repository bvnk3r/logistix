package org.logistix.products.mapper;

import org.logistix.products.dto.CreateProductDto;
import org.logistix.products.dto.ProductDto;
import org.logistix.products.dto.ProductInfoDto;
import org.logistix.products.dto.UpdateProductDto;
import org.logistix.products.model.Product;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProductMapper {

    Product fromCreateDto(CreateProductDto dto);

    ProductDto toDto(Product entity);

    ProductInfoDto toInfoDto(Product entity);

    void update(UpdateProductDto dto, @MappingTarget Product entity);
}