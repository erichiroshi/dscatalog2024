CREATE TABLE tb_categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_tb_categories PRIMARY KEY (id)
);