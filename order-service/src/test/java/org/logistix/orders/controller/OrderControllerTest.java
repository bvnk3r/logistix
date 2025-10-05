package org.logistix.orders.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.logistix.orders.OrderServiceBaseTest;
import org.logistix.orders.dto.client.ClientInfoDto;
import org.logistix.orders.dto.product.ProductInfoDto;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName(value = "Orders controller test")
class OrderControllerTest extends OrderServiceBaseTest {

    private UUID orderId;

    @BeforeEach
    void setUp() {
        orderId = orderRepository.save(testObjectFactory.newOrderEntity("1")).getId();
        when(restService.getClientInfo(any())).thenReturn(new ClientInfoDto("name", "address", "phone", "UL"));
        when(restService.getProductInfo(any())).thenReturn(
                new ProductInfoDto("code", "name", new BigDecimal(1), new BigDecimal(10)));
    }

    @AfterEach
    void shutDown() {
        orderRepository.deleteAll();
    }


    @Test
    void getOrderInfoTest() throws Exception {
        mvc.perform(get("/api/v1/order/{orderId}/info",  orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderNumber", equalTo("1")))
                .andExpect(jsonPath("$.clientName", equalTo("name")))
                .andExpect(jsonPath("$.clientPhone", equalTo("phone")))
                .andExpect(jsonPath("$.deliveryAddress", equalTo("address")))
                .andExpect(jsonPath("$.status", equalTo("CREATED")))
                .andExpect(jsonPath("$.items[0].productCode", equalTo("code")))
                .andExpect(jsonPath("$.items[0].productName", equalTo("name")))
                .andExpect(jsonPath("$.items[0].quantity", equalTo(10)))
                .andExpect(jsonPath("$.items[0].price", equalTo(100)));
    }

    @Test
    void createOrderTest() throws Exception {
        var createOrderDto = testObjectFactory.createOrderDto("2");
        mvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createOrderDto)))
                .andExpect(status().isOk());
        assertEquals(2, orderRepository.findAll().size());
    }
}
