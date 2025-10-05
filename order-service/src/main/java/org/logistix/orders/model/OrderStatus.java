package org.logistix.orders.model;

public enum OrderStatus {
    CREATED,
    PAYMENT_REQUESTED,
    PACKAGING,
    DELIVERING,
    CANCELLED,
    COMPLETED,
    ERROR
}
