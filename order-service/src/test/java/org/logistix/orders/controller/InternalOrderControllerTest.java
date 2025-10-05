package org.logistix.orders.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.logistix.orders.OrderServiceBaseTest;
import org.logistix.orders.dto.product.ProductInfoDto;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName(value = "Internal orders controller test")
class InternalOrderControllerTest extends OrderServiceBaseTest {

    private UUID orderId;

    @BeforeEach
    void setUp() {
        orderId = orderRepository.save(testObjectFactory.newOrderEntity("1")).getId();
        when(restService.getProductInfo(any())).thenReturn(
                new ProductInfoDto("code", "name", new BigDecimal(1), new BigDecimal(10)));
    }

    @AfterEach
    void shutDown() {
        orderRepository.deleteAll();
    }

    @Test
    void getOrdersTest() throws Exception {
        mvc.perform(get("/internal/api/v1/order"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(1))))
                .andExpect(jsonPath("$.content[0].orderNumber", equalTo("1")))
                .andExpect(jsonPath("$.content[0].deliveryAddress", equalTo("address")))
                .andExpect(jsonPath("$.content[0].status", equalTo("CREATED")))
                .andExpect(jsonPath("$.content[0].items[0].productCode", equalTo("code")))
                .andExpect(jsonPath("$.content[0].items[0].productName", equalTo("name")))
                .andExpect(jsonPath("$.content[0].items[0].quantity", equalTo(10)))
                .andExpect(jsonPath("$.content[0].items[0].price", equalTo(100)));
    }

    @Test
    void getOrderTest() throws Exception {
        mvc.perform(get("/internal/api/v1/order/{orderId}",  orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderNumber", equalTo("1")))
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
        mvc.perform(post("/internal/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createOrderDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderNumber", equalTo("2")))
                .andExpect(jsonPath("$.deliveryAddress", equalTo("address")))
                .andExpect(jsonPath("$.status", equalTo("CREATED")))
                .andExpect(jsonPath("$.items[0].productCode", equalTo("code")))
                .andExpect(jsonPath("$.items[0].productName", equalTo("name")))
                .andExpect(jsonPath("$.items[0].quantity", equalTo(1)))
                .andExpect(jsonPath("$.items[0].price", equalTo(10)));
        assertEquals(2, orderRepository.findAll().size());
    }

    @Test
    void updateOrderTest() throws Exception {
        var updateOrderDto = testObjectFactory.updateOrderDto();
        mvc.perform(put("/internal/api/v1/order/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateOrderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].price", equalTo(50)))
                .andExpect(jsonPath("$.items[0].productCode", equalTo("code")))
                .andExpect(jsonPath("$.items[0].productName", equalTo("name")))
                .andExpect(jsonPath("$.items[0].quantity", equalTo(2)));
    }

    @Test
    @SneakyThrows
    void deleteOrderTest() {
        mvc.perform(delete("/internal/api/v1/order/{orderId}", orderId))
                .andExpect(status().isOk());
    }
}
