CREATE TABLE orders
(
    id                 uuid         NOT NULL primary key,
    order_number       varchar(50)  NOT NULL UNIQUE,
    delivery_address   varchar(255) NOT NULL,
    client_id          uuid         NOT NULL,
    status             varchar(16)  NOT NULL,
    created_date       timestamp with time zone NULL,
    last_modified_date timestamp with time zone NULL
);

CREATE TABLE order_items
(
    id                 uuid         NOT NULL primary key,
    order_id           uuid         NOT NULL,
    quantity           smallint     NOT NULL,
    price              decimal      NOT NULL,
    product_code       varchar(16)  NOT NULL,
    product_name       varchar(255) NOT NULL,
    created_date       timestamp with time zone NULL,
    last_modified_date timestamp with time zone NULL
);

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id);