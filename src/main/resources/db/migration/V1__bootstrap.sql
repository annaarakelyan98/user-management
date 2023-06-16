create table public.users
(
    id       BIGSERIAL PRIMARY KEY,
    name     varchar(256)                         NOT NULL,
    age      INT CHECK (age >= 18 AND Age <= 999) not null,
    username varchar(256)                         NOT NULL UNIQUE,
    address  varchar(256)
);