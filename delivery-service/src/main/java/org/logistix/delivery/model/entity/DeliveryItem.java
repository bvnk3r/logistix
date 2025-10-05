package org.logistix.delivery.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "delivery_items")
@EntityListeners({AuditingEntityListener.class})
public class DeliveryItem {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="delivery_id", nullable=false)
    @EqualsAndHashCode.Exclude
    private Delivery delivery;

    @Column
    private int quantity;

    @Column
    private BigDecimal price;

    @Column
    private String productCode;

    @Column
    private String productName;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
}
