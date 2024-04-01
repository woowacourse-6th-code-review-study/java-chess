CREATE TABLE pieces
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    position VARCHAR(4),
    team     VARCHAR(8),
    type     VARCHAR(8),

    PRIMARY KEY (id)
);
