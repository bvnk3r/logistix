package org.logistix.clients.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.logistix.clients.ClientServiceBaseTest;
import org.logistix.clients.model.ClientType;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName(value = "Clients controller test")
class ClientControllerTest extends ClientServiceBaseTest {

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
    void getClientTest() throws Exception {
        mvc.perform(get("/api/v1/client/{clientId}/info",  clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.address", equalTo("address")))
                .andExpect(jsonPath("$.phone", equalTo("phone")))
                .andExpect(jsonPath("$.type", equalTo(ClientType.UL.toString())));

    }
}
