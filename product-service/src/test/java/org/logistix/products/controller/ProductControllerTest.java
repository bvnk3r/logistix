package org.logistix.products.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.logistix.products.ProductServiceBaseTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName(value = "Products controller test")
class ProductControllerTest extends ProductServiceBaseTest {

    private String code;

    @BeforeEach
    void setUp() {
        code = productRepository.save(testObjectFactory.newProductEntity()).getCode();
    }

    @AfterEach
    void shutDown() {
        productRepository.deleteAll();
    }


    @Test
    void getProductTest() throws Exception {
        mvc.perform(get("/api/v1/product/{code}/info",  code))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", equalTo("code")))
                .andExpect(jsonPath("$.place", equalTo("place")))
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.unit", equalTo("pcs")))
                .andExpect(jsonPath("$.amount", equalTo(1)));
    }
}
