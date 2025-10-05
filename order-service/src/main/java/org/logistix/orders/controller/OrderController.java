package org.logistix.orders.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.logistix.orders.dto.order.CreateOrderDto;
import org.logistix.orders.dto.order.OrderDto;
import org.logistix.orders.dto.order.OrderInfoDto;
import org.logistix.orders.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Получить информацию о заказе", tags = "order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Информация о заказе"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден")
            })
    @GetMapping("/{orderId}/info")
    public OrderInfoDto getOrderInfo(@PathVariable UUID orderId) {
        return orderService.getOrderInfo(orderId);
    }

    @Operation(summary = "Создать заказ", tags = "order", responses = {
            @ApiResponse(responseCode = "200", description = "Созданный заказ"),
            @ApiResponse(responseCode = "400", description = "Заказ не найден")
    })
    @PostMapping
    public OrderDto create(@Valid @RequestBody CreateOrderDto createOrderDto) {
        return orderService.create(createOrderDto);
    }
}
