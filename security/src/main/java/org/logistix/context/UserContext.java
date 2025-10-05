package org.logistix.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserContext {

    public static final String CORRELATION_ID = "tmx-correlation-id";

    private String correlationId;
    private String authToken;
}
