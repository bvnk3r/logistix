CREATE TABLE products
(
    id                 uuid        NOT NULL primary key,
    code               varchar(16) NOT NULL UNIQUE,
    name               varchar     NOT NULL,
    unit               varchar(16) NOT NULL,
    place              varchar(16) NOT NULL,
    amount             decimal     NOT NULL,
    price              decimal     NOT NULL,
    created_date       timestamp with time zone NULL,
    last_modified_date timestamp with time zone NULL
);