CREATE TABLE piatto
(
    nome        VARCHAR(100) PRIMARY KEY,
    ingredienti VARCHAR(250)
);


CREATE TABLE ristorante
(
    nome        VARCHAR(100) PRIMARY KEY,
    descrizione VARCHAR(250),
    ubicazione VARCHAR(250)
);


CREATE TABLE ristorante_piatto
(
    ristorante_nome VARCHAR(100),
    piatto_nome     VARCHAR(100),
    PRIMARY KEY (ristorante_nome, piatto_nome),
    FOREIGN KEY (ristorante_nome) REFERENCES ristorante(nome) ON DELETE CASCADE,
    FOREIGN KEY (piatto_nome) REFERENCES piatto(nome) ON DELETE CASCADE
);


