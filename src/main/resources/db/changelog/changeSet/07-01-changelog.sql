-- liquibase formatted sql

-- changeset onish:1757263640055-1
ALTER TABLE users
    ADD role SMALLINT;

