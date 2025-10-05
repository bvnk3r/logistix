package org.logistix.delivery;

import org.logistix.common.messaging.delivery.DeliveryStatus;
import org.logistix.delivery.dto.CreateDeliveryDto;
import org.logistix.delivery.dto.UpdateDeliveryDto;
import org.logistix.delivery.model.entity.Delivery;
import org.logistix.delivery.model.entity.DeliveryItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;

@Component
public class TestObjectFactory {

    public CreateDeliveryDto createDeliveryDto(String number) {
        var deliveryItemDto = new CreateDeliveryDto.DeliveryItemDto(
                1,
                BigDecimal.valueOf(10),
                "code",
                "product 1"
        );

        var items = new HashSet<CreateDeliveryDto.DeliveryItemDto>();
        items.add(deliveryItemDto);

        return new CreateDeliveryDto(
                number,
                "address 1",
                items
        );
    }

    public UpdateDeliveryDto updateDeliveryDto() {
        var deliveryItemDto = new UpdateDeliveryDto.DeliveryItemDto(
                2,
                BigDecimal.valueOf(50),
                "new_code",
                "product 2"
        );

        var items = new HashSet<UpdateDeliveryDto.DeliveryItemDto>();
        items.add(deliveryItemDto);

        return new UpdateDeliveryDto(
                "address 2",
                Instant.now(),
                DeliveryStatus.PACKAGING,
                items
        );
    }

    public Delivery newDeliveryEntity(String number) {
        var delivery = Delivery.builder()
                .orderNumber(number)
                .deliveryAddress("address 1")
                .deliveryDate(Instant.now())
                .status(DeliveryStatus.CREATED)
                .build();

        var items = new HashSet<DeliveryItem>();
        items.add(newDeliveryItemEntity(delivery));
        items.add(newDeliveryItemEntity(delivery));
        items.add(newDeliveryItemEntity(delivery));

        delivery.setItems(items);

        return delivery;
    }

    public DeliveryItem newDeliveryItemEntity(Delivery delivery) {
        return DeliveryItem.builder()
                .delivery(delivery)
                .price(BigDecimal.valueOf(100))
                .productCode("code")
                .productName("product 1")
                .quantity(10)
                .build();
    }
}
