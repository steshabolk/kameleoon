--liquibase formatted sql

--changeset steshabolk:1
CREATE TABLE IF NOT EXISTS users
(
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name                VARCHAR(64)         NOT NULL,
    email               VARCHAR(64)  UNIQUE NOT NULL,
    password            VARCHAR             NOT NULL,
    created_at          TIMESTAMP           NOT NULL
);
--rollback DROP TABLE users;

--changeset steshabolk:2
CREATE TABLE IF NOT EXISTS quotes
(
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id             BIGINT REFERENCES users (id) ON DELETE CASCADE,
    quote               VARCHAR     UNIQUE  NOT NULL,
    created_at          TIMESTAMP           NOT NULL,
    updated_at          TIMESTAMP           NOT NULL
);
--rollback DROP TABLE quotes;

--changeset steshabolk:3
CREATE TABLE IF NOT EXISTS votes
(
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    quote_id            BIGINT REFERENCES quotes (id) ON DELETE CASCADE,
    user_id             BIGINT REFERENCES users (id) ON DELETE CASCADE,
    created_at          TIMESTAMP           NOT NULL,
    vote                INT                 NOT NULL
);
--rollback DROP TABLE votes;

--changeset steshabolk:4
CREATE TABLE IF NOT EXISTS scores
(
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    quote_id            BIGINT REFERENCES quotes (id) ON DELETE CASCADE,
    score               INT                 NOT NULL,
    updated_at          TIMESTAMP           NOT NULL
);
--rollback DROP TABLE scores;
