package org.logistix.clients.mapper;

import org.logistix.clients.dto.ClientInfoDto;
import org.logistix.clients.dto.CreateClientDto;
import org.logistix.clients.dto.UpdateClientDto;
import org.logistix.clients.dto.ClientDto;
import org.logistix.clients.model.entity.Client;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ClientMapper {

    Client fromCreateDto(CreateClientDto dto);

    ClientDto toDto(Client entity);

    ClientInfoDto toInfoDto(Client entity);

    void update(UpdateClientDto dto, @MappingTarget Client entity);
}