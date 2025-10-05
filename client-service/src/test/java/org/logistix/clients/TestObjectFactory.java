package org.logistix.clients;

import org.logistix.clients.dto.CreateClientDto;
import org.logistix.clients.dto.UpdateClientDto;
import org.logistix.clients.model.ClientType;
import org.logistix.clients.model.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class TestObjectFactory {

    public CreateClientDto createClientDto() {
        return new CreateClientDto(
                "name",
                "address",
                "phone",
                ClientType.UL
        );
    }

    public UpdateClientDto updateClientDto() {
        return new UpdateClientDto(
                "name1",
                "address1",
                "phone1",
                ClientType.FL
        );
    }

    public Client newClientEntity() {
        return Client.builder()
                .address("address")
                .name("name")
                .phone("phone")
                .type(ClientType.UL)
                .build();
    }
}
