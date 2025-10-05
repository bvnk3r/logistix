CREATE TABLE deliveries
(
    id                 uuid         NOT NULL primary key,
    order_number       varchar(50)  NOT NULL UNIQUE,
    delivery_address   varchar(255) NOT NULL,
    delivery_date      timestamp with time zone NULL,
    status             varchar(16)  NOT NULL,
    created_date       timestamp with time zone NULL,
    last_modified_date timestamp with time zone NULL
);

CREATE TABLE delivery_items
(
    id                 uuid         NOT NULL primary key,
    delivery_id        uuid         NOT NULL,
    quantity           smallint     NOT NULL,
    price              decimal      NOT NULL,
    product_code       varchar(16)  NOT NULL,
    product_name       varchar(255) NOT NULL,
    created_date       timestamp with time zone NULL,
    last_modified_date timestamp with time zone NULL
);

ALTER TABLE delivery_items
    ADD CONSTRAINT fk_delivery_items_order FOREIGN KEY (delivery_id) REFERENCES deliveries (id);