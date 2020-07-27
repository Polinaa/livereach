CREATE TABLE reference (
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    embed_id            INTEGER NOT NULL,
    product_id          VARCHAR(255) NOT NULL
)

CREATE TABLE availability (
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    content_id          BIGSERIAL REFERENCES reference (id) ON DELETE CASCADE NOT NULL,
    timestamp           BIGINT                NOT NULL,
    available           BOOLEAN               NOT NULL
);