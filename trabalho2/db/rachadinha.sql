CREATE DATABASE IF NOT EXISTS `racha_db`
CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE `racha_db`;

-- 1. Users: Added UNIQUE to email
CREATE TABLE `usuarios` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefone` varchar(50) DEFAULT NULL, -- 255 is too big for phone
  `avatar_id` int NOT NULL DEFAULT '1',
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2. Friends: Unchanged, looks good
CREATE TABLE `amigos` (
  `id_usuario` bigint NOT NULL,
  `id_amigo` bigint NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_amigo`),
  KEY `id_amigo` (`id_amigo`),
  CONSTRAINT `amigos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE,
  CONSTRAINT `amigos_ibfk_2` FOREIGN KEY (`id_amigo`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- racha_db.rachas definition
CREATE TABLE `rachas` (
  `id_racha` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `criado_em` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('ABERTO','FECHADO') DEFAULT 'ABERTO',
  `location` point DEFAULT NULL,
  `local_nome` varchar(255) DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_racha`),
  KEY `fk_racha_owner` (`owner_id`),
  CONSTRAINT `fk_racha_owner` FOREIGN KEY (`owner_id`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- racha_db.itens_racha definition
CREATE TABLE `itens_racha` (
  `id_item_racha` bigint NOT NULL AUTO_INCREMENT,
  `id_racha` bigint NOT NULL,
  `nome` varchar(255) NOT NULL,
  `preco` decimal(38,2) NOT NULL,
  `payer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_item_racha`),
  KEY `id_racha` (`id_racha`),
  KEY `fk_item_payer` (`payer_id`),
  CONSTRAINT `fk_item_payer` FOREIGN KEY (`payer_id`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `itens_racha_ibfk_1` FOREIGN KEY (`id_racha`) REFERENCES `rachas` (`id_racha`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 5. Devedores: Changed percentual to Decimal
CREATE TABLE `devedores` (
  `id_item_racha` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  `percentual` decimal(5,2) DEFAULT NULL, -- Allows 33.33
  PRIMARY KEY (`id_item_racha`,`id_usuario`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `devedores_ibfk_1` FOREIGN KEY (`id_item_racha`) REFERENCES `itens_racha` (`id_item_racha`) ON DELETE CASCADE,
  CONSTRAINT `devedores_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 6. Credores: Adjusted Decimal
CREATE TABLE `credores` (
  `id_racha` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  `valor_pago` decimal(12,2) NOT NULL,
  PRIMARY KEY (`id_racha`,`id_usuario`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `credores_ibfk_1` FOREIGN KEY (`id_racha`) REFERENCES `rachas` (`id_racha`) ON DELETE CASCADE,
  CONSTRAINT `credores_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 7. Pagamentos: Adjusted Decimal
CREATE TABLE `pagamentos` (
  `id_pagamento` bigint NOT NULL AUTO_INCREMENT,
  `id_racha` bigint NOT NULL,
  `id_devedor` bigint NOT NULL,
  `id_credor` bigint NOT NULL,
  `valor` decimal(12,2) NOT NULL,
  `data` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_pagamento`),
  KEY `id_racha` (`id_racha`),
  KEY `id_devedor` (`id_devedor`),
  KEY `id_credor` (`id_credor`),
  CONSTRAINT `pagamentos_ibfk_1` FOREIGN KEY (`id_racha`) REFERENCES `rachas` (`id_racha`),
  CONSTRAINT `pagamentos_ibfk_2` FOREIGN KEY (`id_devedor`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `pagamentos_ibfk_3` FOREIGN KEY (`id_credor`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;