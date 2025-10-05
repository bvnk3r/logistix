package org.logistix.orders.mapper;

import lombok.extern.slf4j.Slf4j;
import org.logistix.orders.dto.client.ClientInfoDto;
import org.logistix.orders.dto.order.*;
import org.logistix.orders.exception.ServiceUnavailableException;
import org.logistix.orders.model.entity.Order;
import org.logistix.orders.model.entity.OrderItem;
import org.logistix.orders.service.RestService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Slf4j
@Mapper(componentModel = SPRING)
public abstract class OrderMapper {

    @Autowired
    protected RestService restService;

    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "items", source = "items", qualifiedByName = "mapOrderItems")
    public abstract Order fromCreateDto(CreateOrderDto dto);

    public abstract OrderDto toDto(Order entity);

    @Mapping(target = "clientName", source = "clientInfo.name")
    @Mapping(target = "clientPhone", source = "clientInfo.phone")
    public abstract OrderInfoDto toInfoDto(Order entity, ClientInfoDto clientInfo);

    public abstract void update(UpdateOrderDto dto, @MappingTarget Order entity);

    @AfterMapping
    protected void setOrderToOrderItems(@MappingTarget Order entity) {
        entity.getItems().forEach(i -> i.setOrder(entity));
    }

    @Named("mapOrderItems")
    protected Set<OrderItem> mapOrderItems(Set<CreateOrderDto.OrderItemDto> items) {
            return items.stream().map(i -> {
                try {
                    var productInfo = restService.getProductInfo(i.productCode());
                    return OrderItem.builder()
                            .quantity(i.quantity())
                            .price(productInfo.price())
                            .productName(productInfo.name())
                            .productCode(i.productCode())
                            .build();
                } catch (Exception e) {
//                    throw new ServiceUnavailableException(
//                            "Failed to fetch info for product with code " + i.productCode());
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toSet());

    }
}