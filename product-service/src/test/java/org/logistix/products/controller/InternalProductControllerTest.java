package org.logistix.products.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.logistix.products.ProductServiceBaseTest;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName(value = "Internal products controller test")
class InternalProductControllerTest extends ProductServiceBaseTest {

    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = productRepository.save(testObjectFactory.newProductEntity()).getId();
    }

    @AfterEach
    void shutDown() {
        productRepository.deleteAll();
    }

    @Test
    void getProductsTest() throws Exception {
        mvc.perform(get("/internal/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(1))))
                .andExpect(jsonPath("$.content[0].code", equalTo("code")))
                .andExpect(jsonPath("$.content[0].unit", equalTo("pcs")))
                .andExpect(jsonPath("$.content[0].name", equalTo("name")))
                .andExpect(jsonPath("$.content[0].place", equalTo("place")));
    }

    @Test
    void getProductTest() throws Exception {
        mvc.perform(get("/internal/api/v1/product/{productId}",  productId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", equalTo("code")))
                .andExpect(jsonPath("$.unit", equalTo("pcs")))
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.place", equalTo("place")));
    }

    @Test
    void createProductTest() throws Exception {
        var createProductDto = testObjectFactory.createProductDto("code1");
        mvc.perform(post("/internal/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createProductDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", equalTo("code1")))
                .andExpect(jsonPath("$.unit", equalTo("pcs")))
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.place", equalTo("place")));
        assertEquals(2, productRepository.findAll().size());
    }

    @Test
    void updateProductTest() throws Exception {
        var updateProductDto = testObjectFactory.updateProductDto();
        mvc.perform(put("/internal/api/v1/product/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", equalTo(5)))
                .andExpect(jsonPath("$.unit", equalTo("oz")))
                .andExpect(jsonPath("$.name", equalTo("name1")))
                .andExpect(jsonPath("$.place", equalTo("place1")))
                .andExpect(jsonPath("$.amount", equalTo(2)));
    }

    @Test
    @SneakyThrows
    void deleteProductTest() {
        mvc.perform(delete("/internal/api/v1/product/{productId}", productId))
                .andExpect(status().isOk());
    }
}
