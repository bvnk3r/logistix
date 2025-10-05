package org.logistix.payment;

import org.logistix.common.messaging.payment.PaymentStatus;
import org.logistix.payment.dto.CreatePaymentDto;
import org.logistix.payment.dto.UpdatePaymentDto;
import org.logistix.payment.model.entity.Payment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class TestObjectFactory {

    public CreatePaymentDto createPaymentDto(String number) {
        return new CreatePaymentDto(
                number,
                new BigDecimal(100)
        );
    }

    public UpdatePaymentDto updatePaymentDto() {
        return new UpdatePaymentDto(
                new BigDecimal(110),
                PaymentStatus.COMPLETED
        );
    }

    public Payment newPaymentEntity(String number) {
        return Payment.builder()
                .orderNumber(number)
                .amount(new BigDecimal(100))
                .paymentDate(Instant.now())
                .status(PaymentStatus.CREATED)
                .build();
    }
}
