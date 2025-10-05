package org.logistix.delivery.controller.internal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.logistix.delivery.dto.CreateDeliveryDto;
import org.logistix.delivery.dto.DeliveryDto;
import org.logistix.delivery.dto.UpdateDeliveryDto;
import org.logistix.delivery.service.internal.InternalDeliveryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/api/v1/delivery")
public class InternalDeliveryController {

    private final InternalDeliveryService internalDeliveryService;

    @Operation(summary = "Получить список доставок", tags = "internal-delivery", responses = {
            @ApiResponse(responseCode = "200", description = "Список доставок")
    })
    @GetMapping
    public Page<DeliveryDto> list(
            @SortDefault(sort = "lastModifiedDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return internalDeliveryService.list(pageable);
    }

    @Operation(summary = "Получить доставку", tags = "internal-delivery",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Доставка"),
                    @ApiResponse(responseCode = "404", description = "Доставка не найдена")
            })
    @GetMapping("/{deliveryId}")
    public DeliveryDto getById(@PathVariable UUID deliveryId) {
        return internalDeliveryService.getById(deliveryId);
    }

    @Operation(summary = "Создать доставку", tags = "internal-delivery", responses = {
            @ApiResponse(responseCode = "200", description = "Созданная доставка"),
            @ApiResponse(responseCode = "400", description = "Доставка не найдена")
    })
    @PostMapping
    public DeliveryDto create(@Valid @RequestBody CreateDeliveryDto createDeliveryDto) {
        return internalDeliveryService.create(createDeliveryDto);
    }

    @Operation(summary = "Обновить доставку", tags = "internal-delivery", responses = {
            @ApiResponse(responseCode = "200", description = "Обновленная доставка"),
            @ApiResponse(responseCode = "404", description = "Доставка не найдена")
    })
    @PutMapping("/{deliveryId}")
    public DeliveryDto update(
            @PathVariable UUID deliveryId, @Valid @RequestBody UpdateDeliveryDto updateDeliveryDto) {
        return internalDeliveryService.update(deliveryId, updateDeliveryDto);
    }

    @Operation(summary = "Удалить доставку", tags = "internal-delivery", responses = {
            @ApiResponse(responseCode = "200", description = "Доставка удалена"),
            @ApiResponse(responseCode = "404", description = "Доставка не найдена")
    })
    @DeleteMapping("/{deliveryId}")
    public void delete(@PathVariable UUID deliveryId) {
        internalDeliveryService.delete(deliveryId);
    }
}
