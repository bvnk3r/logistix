package org.logistix.delivery.service.internal;

import lombok.RequiredArgsConstructor;

import org.logistix.delivery.dto.CreateDeliveryDto;
import org.logistix.delivery.dto.DeliveryDto;
import org.logistix.delivery.dto.UpdateDeliveryDto;
import org.logistix.delivery.exception.EntityNotFoundException;
import org.logistix.delivery.mapper.DeliveryMapper;
import org.logistix.delivery.repository.DeliveryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class InternalDeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    public Page<DeliveryDto> list(Pageable pageable) {
        return deliveryRepository.findAll(pageable).map(deliveryMapper::toDto);
    }

    public DeliveryDto getById(UUID id) {
        return deliveryMapper.toDto(deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found, id=" + id)));
    }

    public DeliveryDto create(CreateDeliveryDto createDeliveryDto) {
        return deliveryMapper.toDto(deliveryRepository.save(deliveryMapper.fromCreateDto(createDeliveryDto)));
    }

    public DeliveryDto update(UUID deliveryId, UpdateDeliveryDto deliveryDto) {
        var delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found, id=" + deliveryId));
        deliveryMapper.update(deliveryDto, delivery);
        return deliveryMapper.toDto(deliveryRepository.save(delivery));
    }

    public void delete(UUID id) {
        deliveryRepository.deleteById(id);
    }
}
