CREATE DATABASE `racha_db`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE racha_db;

CREATE TABLE usuarios (
  id_usuario BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(256) NOT NULL,
  email VARCHAR(256) NOT NULL,
  telefone VARCHAR(50),
  PRIMARY KEY (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE amigos (
  id_usuario BIGINT NOT NULL,
  id_amigo   BIGINT NOT NULL,
  PRIMARY KEY (id_usuario, id_amigo),
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
  FOREIGN KEY (id_amigo)   REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE rachas (
  id_racha BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(256) NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  location POINT NOT NULL,
  PRIMARY KEY (id_racha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE itens_racha (
  id_item_racha BIGINT NOT NULL AUTO_INCREMENT,
  id_racha      BIGINT NOT NULL,
  nome VARCHAR(256) NOT NULL,
  preco DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_item_racha),
  FOREIGN KEY (id_racha) REFERENCES rachas(id_racha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE credores (
  id_racha   BIGINT NOT NULL,
  id_usuario BIGINT NOT NULL,
  valor_pago DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_racha, id_usuario),
  FOREIGN KEY (id_racha)   REFERENCES rachas(id_racha),
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE devedores (
  id_item_racha BIGINT NOT NULL,
  id_usuario    BIGINT NOT NULL,
  percentual    INT,
  PRIMARY KEY (id_item_racha, id_usuario),
  FOREIGN KEY (id_item_racha) REFERENCES itens_racha(id_item_racha),
  FOREIGN KEY (id_usuario)    REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE pagamentos (
  id_pagamento BIGINT NOT NULL AUTO_INCREMENT,
  id_racha     BIGINT NOT NULL,
  id_devedor   BIGINT NOT NULL,
  id_credor    BIGINT NOT NULL,
  valor        DECIMAL(10,2) NOT NULL,
  data         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_pagamento),
  FOREIGN KEY (id_racha)   REFERENCES rachas(id_racha),
  FOREIGN KEY (id_devedor) REFERENCES usuarios(id_usuario),
  FOREIGN KEY (id_credor)  REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

