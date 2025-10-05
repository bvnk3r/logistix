package org.logistix.delivery.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.logistix.common.messaging.delivery.DeliveryStatus;
import org.logistix.delivery.DeliveryServiceBaseTest;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName(value = "Internal delivery controller test")
class InternalDeliveryControllerTest extends DeliveryServiceBaseTest {

    private UUID deliveryId;

    @BeforeEach
    void setUp() {
        deliveryId = deliveryRepository.save(testObjectFactory.newDeliveryEntity("1")).getId();
    }

    @AfterEach
    void shutDown() {
        deliveryRepository.deleteAll();
    }

    @Test
    void getDeliveriesTest() throws Exception {
        mvc.perform(get("/internal/api/v1/delivery"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(1))))
                .andExpect(jsonPath("$.content[0].orderNumber", equalTo("1")))
                .andExpect(jsonPath("$.content[0].deliveryAddress", equalTo("address 1")))
                .andExpect(jsonPath("$.content[0].items[0].price", equalTo(100)))
                .andExpect(jsonPath("$.content[0].items[0].quantity", equalTo(10)))
                .andExpect(jsonPath("$.content[0].items[0].productCode", equalTo("code")))
                .andExpect(jsonPath("$.content[0].items[0].productName", equalTo("product 1")));
    }

    @Test
    void getDeliveryTest() throws Exception {
        mvc.perform(get("/internal/api/v1/delivery/{deliveryId}",  deliveryId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderNumber", equalTo("1")))
                .andExpect(jsonPath("$.deliveryAddress", equalTo("address 1")))
                .andExpect(jsonPath("$.items[0].price", equalTo(100)))
                .andExpect(jsonPath("$.items[0].quantity", equalTo(10)))
                .andExpect(jsonPath("$.items[0].productCode", equalTo("code")))
                .andExpect(jsonPath("$.items[0].productName", equalTo("product 1")));
    }

    @Test
    void createDeliveryTest() throws Exception {
        var createDeliveryDto = testObjectFactory.createDeliveryDto("2");
        mvc.perform(post("/internal/api/v1/delivery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createDeliveryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].price", equalTo(10)))
                .andExpect(jsonPath("$.items[0].quantity", equalTo(1)))
                .andExpect(jsonPath("$.items[0].productCode", equalTo("code")))
                .andExpect(jsonPath("$.items[0].productName", equalTo("product 1")))
                .andExpect(jsonPath("$.status", equalTo(DeliveryStatus.CREATED.toString())))
                .andExpect(jsonPath("$.orderNumber", equalTo("2")))
                .andExpect(jsonPath("$.deliveryAddress", equalTo("address 1")));
        assertEquals(2, deliveryRepository.findAll().size());
    }

    @Test
    void updateDeliveryTest() throws Exception {
        var updateDeliveryDto = testObjectFactory.updateDeliveryDto();
        mvc.perform(put("/internal/api/v1/delivery/{deliveryId}", deliveryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateDeliveryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].price", equalTo(50)))
                .andExpect(jsonPath("$.items[0].quantity", equalTo(2)))
                .andExpect(jsonPath("$.items[0].productCode", equalTo("new_code")))
                .andExpect(jsonPath("$.items[0].productName", equalTo("product 2")))
                .andExpect(jsonPath("$.status", equalTo(DeliveryStatus.PACKAGING.toString())))
                .andExpect(jsonPath("$.deliveryAddress", equalTo("address 2")));
    }

    @Test
    @SneakyThrows
    void deleteDeliveryTest() {
        mvc.perform(delete("/internal/api/v1/delivery/{deliveryId}", deliveryId))
                .andExpect(status().isOk());
    }
}
