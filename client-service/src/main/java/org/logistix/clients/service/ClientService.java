package org.logistix.clients.service;

import lombok.RequiredArgsConstructor;
import org.logistix.clients.dto.ClientInfoDto;
import org.logistix.clients.exception.EntityNotFoundException;
import org.logistix.clients.mapper.ClientMapper;
import org.logistix.clients.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientInfoDto getClientInfo(UUID id) {
        return clientMapper.toInfoDto(clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found, id=" + id)));
    }
}
