package org.logistix.payment.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.logistix.common.messaging.payment.PaymentStatus;
import org.logistix.payment.PaymentServiceBaseTest;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName(value = "Internal payments controller test")
class InternalPaymentControllerTest extends PaymentServiceBaseTest {

    private UUID paymentId;

    @BeforeEach
    void setUp() {
        paymentId = paymentRepository.save(testObjectFactory.newPaymentEntity("1")).getId();
    }

    @AfterEach
    void shutDown() {
        paymentRepository.deleteAll();
    }

    @Test
    void getPaymentsTest() throws Exception {
        mvc.perform(get("/internal/api/v1/payment"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(1))))
                .andExpect(jsonPath("$.content[0].orderNumber", equalTo("1")))
                .andExpect(jsonPath("$.content[0].amount", equalTo(100)));
    }

    @Test
    void getPaymentTest() throws Exception {
        mvc.perform(get("/internal/api/v1/payment/{paymentId}",  paymentId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderNumber", equalTo("1")))
                .andExpect(jsonPath("$.amount", equalTo(100)));
    }

    @Test
    void createPaymentTest() throws Exception {
        var createPaymentDto = testObjectFactory.createPaymentDto("2");
        mvc.perform(post("/internal/api/v1/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createPaymentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber", equalTo("2")))
                .andExpect(jsonPath("$.status", equalTo(PaymentStatus.CREATED.toString())))
                .andExpect(jsonPath("$.amount", equalTo(100)));
        assertEquals(2, paymentRepository.findAll().size());
    }

    @Test
    void updatePaymentTest() throws Exception {
        var updatePaymentDto = testObjectFactory.updatePaymentDto();
        mvc.perform(put("/internal/api/v1/payment/{paymentId}", paymentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updatePaymentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", equalTo(110)))
                .andExpect(jsonPath("$.status", equalTo(PaymentStatus.COMPLETED.toString())));
    }

    @Test
    @SneakyThrows
    void deletePaymentTest() {
        mvc.perform(delete("/internal/api/v1/payment/{paymentId}", paymentId))
                .andExpect(status().isOk());
    }
}
