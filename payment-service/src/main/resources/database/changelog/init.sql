CREATE TABLE payments
(
    id                 uuid        NOT NULL primary key,
    order_number       varchar(50) NOT NULL UNIQUE,
    amount             decimal     NOT NULL,
    payment_date       timestamp with time zone NULL,
    status             varchar(16) NOT NULL,
    created_date       timestamp with time zone NULL,
    last_modified_date timestamp with time zone NULL
);