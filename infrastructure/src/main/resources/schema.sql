CREATE TYPE currency AS ENUM ('USD');

CREATE TABLE products
(
    id       IDENTITY    NOT NULL PRIMARY KEY,
    name     VARCHAR(64) NOT NULL UNIQUE,
    price    DECIMAL     NOT NULL
        CONSTRAINT price_more_than_zero CHECK (0 = price OR 0 < price),
    currency currency
);
