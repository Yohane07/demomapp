-- Création de la table "author" pour H2
CREATE TABLE IF NOT EXISTS author (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

-- Création de la table "book" pour H2
CREATE TABLE IF NOT EXISTS book (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    author_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id)
        REFERENCES author(id)
);