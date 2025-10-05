package org.logistix.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();

        var userContext = UserContextHolder.getUserContext();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getUserContext().getCorrelationId());
        headers.add(HttpHeaders.AUTHORIZATION, UserContextHolder.getUserContext().getAuthToken());

        return execution.execute(request, body);
    }
}
