package org.logistix.cache.mapper;

import org.logistix.cache.dto.ClientInfoDto;
import org.logistix.cache.dto.ProductInfoDto;
import org.logistix.cache.model.ClientInfo;
import org.logistix.cache.model.ProductInfo;
import org.logistix.common.messaging.client.ClientInfoPayload;
import org.logistix.common.messaging.product.ProductInfoPayload;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CacheMapper {

    ClientInfo fromClientInfoDto(ClientInfoDto dto);

    ProductInfo fromProductInfoDto(ProductInfoDto dto);

    ClientInfo fromClientInfoPayload(ClientInfoPayload payload);

    ProductInfo fromProductInfoPayload(ProductInfoPayload payload);

    ClientInfoDto toClientInfoDto(ClientInfo entity);

    ProductInfoDto toProductInfoDto(ProductInfo entity);
}