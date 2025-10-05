package org.logistix.gateway.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.gateway.utils.FilterUtils;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ResponseFilter {

    private final FilterUtils filterUtils;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) ->
           chain.filter(exchange).then(Mono.fromRunnable(() -> {
               HttpHeaders headers = exchange.getRequest().getHeaders();
               String correlationId = filterUtils.getCorrelationId(headers);

               log.debug("Outbound correlation id: {}", correlationId);
               exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, correlationId);
           }));
    }
}
