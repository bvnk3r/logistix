package org.logistix.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.logistix.clients.repository.ClientRepository;
import org.logistix.clients.service.internal.InternalClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ClientServiceBaseTest.ContainerInitializer.class)
public class ClientServiceBaseTest {

    @Autowired
    protected TestObjectFactory testObjectFactory;
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected InternalClientService internalClientService;
    @Autowired
    protected ClientRepository clientRepository;

    public static class ContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            var postgresSQLContainer = new PostgreSQLContainer("postgres:16-alpine")
                    .withDatabaseName("client-service")
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
