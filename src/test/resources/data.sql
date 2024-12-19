CREATE TABLE IF NOT EXISTS users
(
    id          UUID         NOT NULL,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);