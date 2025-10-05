package org.logistix.orders.service.internal;

import lombok.RequiredArgsConstructor;
import org.logistix.orders.dto.order.CreateOrderDto;
import org.logistix.orders.dto.order.OrderDto;
import org.logistix.orders.dto.order.UpdateOrderDto;
import org.logistix.orders.exception.EntityNotFoundException;
import org.logistix.orders.mapper.OrderMapper;
import org.logistix.orders.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class InternalOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Page<OrderDto> list(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toDto);
    }

    public OrderDto getById(UUID id) {
        return orderMapper.toDto(orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found, id=" + id)));
    }

    public OrderDto create(CreateOrderDto createOrderDto) {
        return orderMapper.toDto(orderRepository.save(orderMapper.fromCreateDto(createOrderDto)));
    }

    public OrderDto update(UUID orderId, UpdateOrderDto orderDto) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found, id=" + orderId));
        orderMapper.update(orderDto, order);
        return orderMapper.toDto(orderRepository.save(order));
    }

    public void delete(UUID id) {
        orderRepository.deleteById(id);
    }
}
