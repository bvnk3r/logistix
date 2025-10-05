package org.logistix.orders.controller.internal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.logistix.orders.dto.order.CreateOrderDto;
import org.logistix.orders.dto.order.OrderDto;
import org.logistix.orders.dto.order.UpdateOrderDto;
import org.logistix.orders.service.internal.InternalOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/api/v1/order")
public class InternalOrderController {

    private final InternalOrderService internalOrderService;

    @Operation(summary = "Получить список заказов", tags = "internal-order", responses = {
            @ApiResponse(responseCode = "200", description = "Список заказов")
    })
    @GetMapping
    public Page<OrderDto> list(
            @SortDefault(sort = "lastModifiedDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return internalOrderService.list(pageable);
    }

    @Operation(summary = "Получить заказ", tags = "internal-order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден")
            })
    @GetMapping("/{orderId}")
    public OrderDto getById(@PathVariable UUID orderId) {
        return internalOrderService.getById(orderId);
    }

    @Operation(summary = "Создать заказ", tags = "internal-order", responses = {
            @ApiResponse(responseCode = "200", description = "Созданный заказ"),
            @ApiResponse(responseCode = "400", description = "Заказ не найден")
    })
    @PostMapping
    public OrderDto create(@Valid @RequestBody CreateOrderDto createOrderDto) {
        return internalOrderService.create(createOrderDto);
    }

    @Operation(summary = "Обновить заказ", tags = "internal-order", responses = {
            @ApiResponse(responseCode = "200", description = "Обновленный заказ"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @PutMapping("/{orderId}")
    public OrderDto update(
            @PathVariable UUID orderId, @Valid @RequestBody UpdateOrderDto updateOrderDto) {
        return internalOrderService.update(orderId, updateOrderDto);
    }

    @Operation(summary = "Удалить заказ", tags = "internal-order", responses = {
            @ApiResponse(responseCode = "200", description = "Заказ удален"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @DeleteMapping("/{orderId}")
    public void delete(@PathVariable UUID orderId) {
        internalOrderService.delete(orderId);
    }
}
