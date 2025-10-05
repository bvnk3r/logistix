package org.logistix.clients.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.logistix.clients.ClientServiceBaseTest;
import org.logistix.clients.model.ClientType;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName(value = "Internal clients controller test")
class InternalClientControllerTest extends ClientServiceBaseTest {

    private UUID clientId;

    @BeforeEach
    void setUp() {
        clientId = clientRepository.save(testObjectFactory.newClientEntity()).getId();
    }

    @AfterEach
    void shutDown() {
        clientRepository.deleteAll();
    }

    @Test
    void getClientsTest() throws Exception {
        mvc.perform(get("/internal/api/v1/client"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(1))))
                .andExpect(jsonPath("$.content[0].address", equalTo("address")))
                .andExpect(jsonPath("$.content[0].name", equalTo("name")))
                .andExpect(jsonPath("$.content[0].phone", equalTo("phone")))
                .andExpect(jsonPath("$.content[0].type", equalTo(ClientType.UL.toString())));
    }

    @Test
    void getClientTest() throws Exception {
        mvc.perform(get("/internal/api/v1/client/{clientId}",  clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.address", equalTo("address")))
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.phone", equalTo("phone")))
                .andExpect(jsonPath("$.type", equalTo(ClientType.UL.toString())));

    }

    @Test
    void createClientTest() throws Exception {
        var createClientDto = testObjectFactory.createClientDto();
        mvc.perform(post("/internal/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createClientDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.address", equalTo("address")))
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.phone", equalTo("phone")))
                .andExpect(jsonPath("$.type", equalTo(ClientType.UL.toString())));
        assertEquals(2, clientRepository.findAll().size());
    }

    @Test
    void updateClientTest() throws Exception {
        var updateClientDto = testObjectFactory.updateClientDto();
        mvc.perform(put("/internal/api/v1/client/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateClientDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", equalTo(ClientType.FL.toString())))
                .andExpect(jsonPath("$.name", equalTo("name1")))
                .andExpect(jsonPath("$.address", equalTo("address1")))
                .andExpect(jsonPath("$.phone", equalTo("phone1")));
    }

    @Test
    @SneakyThrows
    void deleteClientTest() {
        mvc.perform(delete("/internal/api/v1/client/{clientId}", clientId))
                .andExpect(status().isOk());
    }
}
