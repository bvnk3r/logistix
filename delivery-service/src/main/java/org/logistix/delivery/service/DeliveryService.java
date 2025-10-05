package org.logistix.delivery.service;

import lombok.RequiredArgsConstructor;
import org.logistix.common.messaging.delivery.CreateDeliveryPayload;
import org.logistix.common.messaging.delivery.DeliveryStatus;
import org.logistix.common.messaging.delivery.DeliveryStatusPayload;
import org.logistix.delivery.dto.DeliveryDto;
import org.logistix.delivery.mapper.DeliveryMapper;
import org.logistix.delivery.messaging.OrderDeliveryStatusPublisher;
import org.logistix.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final OrderDeliveryStatusPublisher orderDeliveryStatusPublisher;

    public DeliveryDto create(CreateDeliveryPayload createDeliveryDto) {
        var delivery = deliveryMapper.fromCreatePayload(createDeliveryDto);
        orderDeliveryStatusPublisher.publishOrderDeliveryStatus(
                new DeliveryStatusPayload(createDeliveryDto.orderNumber(), DeliveryStatus.PACKAGING));
        return deliveryMapper.toDto(deliveryRepository.save(delivery));
    }
}
