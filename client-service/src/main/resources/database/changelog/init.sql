CREATE TABLE clients
(
    id                 uuid         NOT NULL primary key,
    name               varchar(255) NOT NULL,
    address            varchar(255) NOT NULL,
    phone              varchar(32)  NOT NULL,
    type               varchar(2)   NOT NULL,
    created_date       timestamp with time zone NULL,
    last_modified_date timestamp with time zone NULL
);