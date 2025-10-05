package org.logistix.payment.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logistix.common.messaging.payment.CreatePaymentPayload;
import org.logistix.payment.service.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService paymentService;

    @Bean
    public Consumer<CreatePaymentPayload> createPaymentSink() {
        return payload -> {
            log.info("Received new payment for order number {}", payload.orderNumber());
            paymentService.create(payload);
        };
    }
}
