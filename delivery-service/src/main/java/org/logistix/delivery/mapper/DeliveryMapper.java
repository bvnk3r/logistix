package org.logistix.delivery.mapper;

import org.logistix.common.messaging.delivery.CreateDeliveryPayload;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import org.logistix.delivery.dto.CreateDeliveryDto;
import org.logistix.delivery.dto.DeliveryDto;
import org.logistix.delivery.dto.UpdateDeliveryDto;
import org.logistix.delivery.model.entity.Delivery;

@Mapper(componentModel = SPRING)
public interface DeliveryMapper {

    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "deliveryDate", expression = "java(Instant.now().plus(2, java.time.temporal.ChronoUnit.DAYS))")
    Delivery fromCreateDto(CreateDeliveryDto dto);

    @Mapping(target = "status", constant = "PACKAGING")
    @Mapping(target = "deliveryDate", expression = "java(Instant.now().plus(2, java.time.temporal.ChronoUnit.DAYS))")
    Delivery fromCreatePayload(CreateDeliveryPayload payload);

    DeliveryDto toDto(Delivery entity);

    void update(UpdateDeliveryDto dto, @MappingTarget Delivery entity);

    @AfterMapping
    default void setOrderToOrderItems(@MappingTarget Delivery entity) {
        entity.getItems().forEach(i -> i.setDelivery(entity));
    }
}