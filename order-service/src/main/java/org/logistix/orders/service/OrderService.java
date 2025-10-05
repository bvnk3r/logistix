package org.logistix.orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.orders.messaging.delivery.CreateDeliveryPayload;
import org.logistix.orders.dto.order.CreateOrderDto;
import org.logistix.orders.dto.order.OrderDto;
import org.logistix.orders.dto.order.OrderInfoDto;
import org.logistix.orders.messaging.payment.CreatePaymentPayload;
import org.logistix.orders.exception.EntityNotFoundException;
import org.logistix.orders.mapper.OrderMapper;
import org.logistix.orders.messaging.delivery.DeliveryPublisher;
import org.logistix.orders.messaging.payment.PaymentPublisher;
import org.logistix.orders.model.OrderStatus;
import org.logistix.orders.model.entity.OrderItem;
import org.logistix.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestService restService;
    private final DeliveryPublisher deliveryPublisher;
    private final PaymentPublisher paymentPublisher;

    public OrderInfoDto getOrderInfo(UUID id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found, id=" + id));
        var clientInfo = restService.getClientInfo(order.getClientId());
        return orderMapper.toInfoDto(order, clientInfo);
    }

    public OrderDto create(CreateOrderDto createOrderDto) {
        var order = orderRepository.save(orderMapper.fromCreateDto(createOrderDto));
        var amount = order.getItems().stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        paymentPublisher.createPayment(new CreatePaymentPayload(order.getOrderNumber(), amount));
        return orderMapper.toDto(order);
    }

    public void updateOrderStatus(String orderNumber, OrderStatus status) {
        var orderOptional = orderRepository.findByOrderNumber(orderNumber);
        if (orderOptional.isPresent()) {
            var order = orderOptional.get();
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

    public void deliverOrder(String orderNumber) {
        var orderOptional = orderRepository.findByOrderNumber(orderNumber);
        if (orderOptional.isPresent()) {
            var order = orderOptional.get();
            try {
                deliveryPublisher.createDelivery(new CreateDeliveryPayload(
                        order.getOrderNumber(),
                        order.getDeliveryAddress(),
                        order.getItems().stream().map(i -> new CreateDeliveryPayload.DeliveryItemDto(
                                i.getQuantity(),
                                i.getPrice(),
                                i.getProductCode(),
                                i.getProductName()
                        )).collect(Collectors.toSet())
                ));
            } catch (Exception e) {
                log.error("Error creating delivery", e);
                order.setStatus(OrderStatus.ERROR);
                orderRepository.save(order);
            }
        }
    }
}
