package org.logistix.clients.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.logistix.clients.dto.ClientInfoDto;
import org.logistix.clients.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Получить информацию о клиенте", tags = "clients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Информация о клиенте"),
                    @ApiResponse(responseCode = "404", description = "Клиент не найден")
            })
    @GetMapping("/{clientId}/info")
    public ClientInfoDto getClientInfo(@PathVariable UUID clientId) {
        return clientService.getClientInfo(clientId);
    }
}
