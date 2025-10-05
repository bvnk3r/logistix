package org.logistix.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.logistix.orders.repository.OrderRepository;
import org.logistix.orders.service.RestService;
import org.logistix.orders.service.internal.InternalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = OrderServiceBaseTest.ContainerInitializer.class)
public class OrderServiceBaseTest {

    @Autowired
    protected TestObjectFactory testObjectFactory;
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockitoBean
    protected RestService restService;
    @Autowired
    protected OrderRepository orderRepository;

    public static class ContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            var postgresSQLContainer = new PostgreSQLContainer("postgres:16-alpine")
                    .withDatabaseName("order-service")
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
