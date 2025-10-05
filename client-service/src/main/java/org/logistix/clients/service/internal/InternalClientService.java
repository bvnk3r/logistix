package org.logistix.clients.service.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.clients.dto.CreateClientDto;
import org.logistix.clients.dto.UpdateClientDto;
import org.logistix.clients.dto.ClientDto;
import org.logistix.clients.exception.EntityNotFoundException;
import org.logistix.clients.mapper.ClientMapper;
import org.logistix.clients.messaging.ClientInfoPublisher;
import org.logistix.clients.repository.ClientRepository;
import org.logistix.common.messaging.client.ClientInfoPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InternalClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientInfoPublisher clientInfoPublisher;

    public Page<ClientDto> list(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toDto);
    }

    public ClientDto getById(UUID id) {
        return clientMapper.toDto(clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found, id=" + id)));
    }

    public ClientDto create(CreateClientDto createClientDto) {
        var client = clientMapper.fromCreateDto(createClientDto);
        return clientMapper.toDto(clientRepository.save(client));
    }

    public ClientDto update(UUID id, UpdateClientDto clientDto) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found, id=" + id));
        clientMapper.update(clientDto, client);
        var result = clientRepository.save(client);

        try {
            clientInfoPublisher.publishClientUpdate(new ClientInfoPayload(
                    result.getId(),
                    result.getName(),
                    result.getAddress(),
                    result.getPhone(),
                    result.getType().toString()
            ));
        } catch (Exception e) {
            log.error("Failed to send message, cause: {}", e.getMessage());
        }

        return clientMapper.toDto(result);
    }

    public void delete(UUID id) {
        clientRepository.deleteById(id);
    }
}
