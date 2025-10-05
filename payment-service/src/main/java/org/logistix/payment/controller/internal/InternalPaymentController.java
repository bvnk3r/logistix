package org.logistix.payment.controller.internal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.logistix.payment.dto.CreatePaymentDto;
import org.logistix.payment.dto.PaymentDto;
import org.logistix.payment.dto.UpdatePaymentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/api/v1/payment")
public class InternalPaymentController {

    private final org.logistix.payment.service.internal.InternalPaymentService internalPaymentService;

    @Operation(summary = "Получить список платежей", tags = "internal-payment", responses = {
            @ApiResponse(responseCode = "200", description = "Список платежей")
    })
    @GetMapping
    public Page<PaymentDto> list(
            @SortDefault(sort = "lastModifiedDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return internalPaymentService.list(pageable);
    }

    @Operation(summary = "Получить платеж", tags = "internal-payment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Платеж"),
                    @ApiResponse(responseCode = "404", description = "Платеж не найден")
            })
    @GetMapping("/{paymentId}")
    public PaymentDto getById(@PathVariable UUID paymentId) {
        return internalPaymentService.getById(paymentId);
    }

    @Operation(summary = "Создать платеж", tags = "internal-payment", responses = {
            @ApiResponse(responseCode = "200", description = "Созданный платеж"),
            @ApiResponse(responseCode = "400", description = "Платеж не найден")
    })
    @PostMapping
    public PaymentDto create(@Valid @RequestBody CreatePaymentDto createPaymentDto) {
        return internalPaymentService.create(createPaymentDto);
    }

    @Operation(summary = "Обновить платеж", tags = "internal-payment", responses = {
            @ApiResponse(responseCode = "200", description = "Обновленный платеж"),
            @ApiResponse(responseCode = "404", description = "Платеж не найден")
    })
    @PutMapping("/{paymentId}")
    public PaymentDto update(
            @PathVariable UUID paymentId, @Valid @RequestBody UpdatePaymentDto updatePaymentDto) {
        return internalPaymentService.update(paymentId, updatePaymentDto);
    }

    @Operation(summary = "Удалить платеж", tags = "internal-payment", responses = {
            @ApiResponse(responseCode = "200", description = "Платеж удален"),
            @ApiResponse(responseCode = "404", description = "Платеж не найден")
    })
    @DeleteMapping("/{paymentId}")
    public void delete(@PathVariable UUID paymentId) {
        internalPaymentService.delete(paymentId);
    }
}
