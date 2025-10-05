package org.logistix.orders;

import org.logistix.orders.dto.order.CreateOrderDto;
import org.logistix.orders.dto.order.UpdateOrderDto;
import org.logistix.orders.model.OrderStatus;
import org.logistix.orders.model.entity.Order;
import org.logistix.orders.model.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

@Component
public class TestObjectFactory {

    public static final UUID CLIENT_ID = UUID.randomUUID();
    public static final String PRODUCT_CODE = "code";
    public static final String PRODUCT_NAME = "name";
    public static final String DELIVERY_ADDRESS = "address";

    public CreateOrderDto createOrderDto(String number) {
        var orderItemDto = new CreateOrderDto.OrderItemDto(
                1,
                PRODUCT_CODE
        );

        var items = new HashSet<CreateOrderDto.OrderItemDto>();
        items.add(orderItemDto);

        return new CreateOrderDto(
                number,
                DELIVERY_ADDRESS,
                CLIENT_ID,
                items
        );
    }

    public UpdateOrderDto updateOrderDto() {
        var orderItemDto = new UpdateOrderDto.OrderItemDto(
                2,
                BigDecimal.valueOf(50),
                PRODUCT_CODE,
                PRODUCT_NAME
        );

        var items = new HashSet<UpdateOrderDto.OrderItemDto>();
        items.add(orderItemDto);

        return new UpdateOrderDto(
                DELIVERY_ADDRESS,
                CLIENT_ID,
                OrderStatus.CREATED,
                items
        );
    }

    public Order newOrderEntity(String number) {
        var order = Order.builder()
                .clientId(CLIENT_ID)
                .orderNumber(number)
                .deliveryAddress(DELIVERY_ADDRESS)
                .status(OrderStatus.CREATED)
                .build();

        var items = new HashSet<OrderItem>();
        items.add(newOrderItemEntity(order));
        items.add(newOrderItemEntity(order));
        items.add(newOrderItemEntity(order));

        order.setItems(items);

        return order;
    }

    public OrderItem newOrderItemEntity(Order order) {
        return OrderItem.builder()
                .order(order)
                .price(BigDecimal.valueOf(100))
                .productCode(PRODUCT_CODE)
                .productName(PRODUCT_NAME)
                .quantity(10)
                .build();
    }
}
