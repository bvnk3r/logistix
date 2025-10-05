package org.logistix.clients.controller.internal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.logistix.clients.dto.ClientDto;
import org.logistix.clients.dto.CreateClientDto;
import org.logistix.clients.dto.UpdateClientDto;
import org.logistix.clients.service.internal.InternalClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/api/v1/client")
public class InternalClientController {

    private final InternalClientService internalClientService;

    @Operation(summary = "Получить список клиентов", tags = "internal-clients", responses = {
            @ApiResponse(responseCode = "200", description = "Список клиентов")
    })
    @GetMapping
    public Page<ClientDto> list(
            @SortDefault(sort = "lastModifiedDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return internalClientService.list(pageable);
    }

    @Operation(summary = "Получить клиента", tags = "internal-clients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Клиент"),
                    @ApiResponse(responseCode = "404", description = "Клиент не найден")
            })
    @GetMapping("/{clientId}")
    public ClientDto getById(@PathVariable UUID clientId) {
        return internalClientService.getById(clientId);
    }

    @Operation(summary = "Создать клиента", tags = "internal-clients", responses = {
            @ApiResponse(responseCode = "200", description = "Созданный клиент"),
            @ApiResponse(responseCode = "400", description = "Клиент не найден")
    })
    @PostMapping
    public ClientDto create(@Valid @RequestBody CreateClientDto createClientDto) {
        return internalClientService.create(createClientDto);
    }

    @Operation(summary = "Обновить клиента", tags = "internal-clients", responses = {
            @ApiResponse(responseCode = "200", description = "Обновленный клиент"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @PutMapping("/{clientId}")
    public ClientDto update(
            @PathVariable UUID clientId, @Valid @RequestBody UpdateClientDto updateClientDto) {
        return internalClientService.update(clientId, updateClientDto);
    }

    @Operation(summary = "Удалить клиента", tags = "internal-clients", responses = {
            @ApiResponse(responseCode = "200", description = "Клиент удален"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @DeleteMapping("/{clientId}")
    public void delete(@PathVariable UUID clientId) {
        internalClientService.delete(clientId);
    }
}
