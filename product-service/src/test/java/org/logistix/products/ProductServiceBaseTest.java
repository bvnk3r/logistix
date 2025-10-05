package org.logistix.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.logistix.products.repository.ProductRepository;
import org.logistix.products.service.internal.InternalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ProductServiceBaseTest.ContainerInitializer.class)
public class ProductServiceBaseTest {

    @Autowired
    protected TestObjectFactory testObjectFactory;
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected InternalProductService internalProductService;
    @Autowired
    protected ProductRepository productRepository;

    public static class ContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            var postgresSQLContainer = new PostgreSQLContainer("postgres:16-alpine")
                    .withDatabaseName("product-service")
                    .withUsername("postgres")
                    .withPassword("postgres");

            postgresSQLContainer.start();

            TestPropertyValues.of(
                    "spring.datasource.username=" + postgresSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgresSQLContainer.getPassword(),
                    "spring.datasource.url=" + postgresSQLContainer.getJdbcUrl()
            ).applyTo(applicationContext);
            postgresSQLContainer.start();
        }
    }
}
