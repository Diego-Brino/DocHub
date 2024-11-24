-- dochub.atividade definition

CREATE TABLE IF NOT EXISTS `dochub`.`atividade` (
                             `ID_ATIVIDADE` int NOT NULL AUTO_INCREMENT,
                             `DESCRICAO` varchar(256) DEFAULT NULL,
                             `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `DATA_INSERCAO` datetime NOT NULL,
                             `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                             `DATA_ALTERACAO` datetime DEFAULT NULL,
                             PRIMARY KEY (`ID_ATIVIDADE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.cargo definition

CREATE TABLE IF NOT EXISTS `dochub`.`cargo` (
                         `ID_CARGO` int NOT NULL AUTO_INCREMENT,
                         `NOME` varchar(128) NOT NULL,
                         `DESCRICAO` varchar(256) DEFAULT NULL,
                         `COR` varchar(32) NOT NULL,
                         `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `DATA_INSERCAO` datetime NOT NULL,
                         `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `DATA_ALTERACAO` datetime DEFAULT NULL,
                         `STATUS` enum('ATIVO','INATIVO') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         PRIMARY KEY (`ID_CARGO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.grupo definition

CREATE TABLE IF NOT EXISTS `dochub`.`grupo` (
                         `ID_GRUPO` int NOT NULL AUTO_INCREMENT,
                         `ID_S3_BUCKET` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `NOME` varchar(128) NOT NULL,
                         `DESCRICAO` varchar(256) DEFAULT NULL,
                         `AVATAR` longblob,
                         `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `DATA_INSERCAO` datetime NOT NULL,
                         `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `DATA_ALTERACAO` datetime DEFAULT NULL,
                         PRIMARY KEY (`ID_GRUPO`),
                         UNIQUE KEY `grupo_unique` (`ID_S3_BUCKET`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- dochub.permissao_grupo definition

CREATE TABLE IF NOT EXISTS `dochub`.`permissao_grupo` (
                                   `ID_PERMISSAO_GRUPO` int NOT NULL AUTO_INCREMENT,
                                   `DESCRICAO` varchar(128) NOT NULL,
                                   `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   `DATA_INSERCAO` datetime NOT NULL,
                                   `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                   `DATA_ALTERACAO` datetime DEFAULT NULL,
                                   PRIMARY KEY (`ID_PERMISSAO_GRUPO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.permissao_recurso definition

CREATE TABLE IF NOT EXISTS `dochub`.`permissao_recurso` (
                                     `ID_PERMISSAO_RECURSO` int NOT NULL AUTO_INCREMENT,
                                     `DESCRICAO` varchar(128) NOT NULL,
                                     `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                     `DATA_INSERCAO` datetime NOT NULL,
                                     `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                     `DATA_ALTERACAO` datetime DEFAULT NULL,
                                     PRIMARY KEY (`ID_PERMISSAO_RECURSO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.permissao_sistema definition

CREATE TABLE IF NOT EXISTS `dochub`.`permissao_sistema` (
                                     `ID_PERMISSAO_SISTEMA` int NOT NULL AUTO_INCREMENT,
                                     `DESCRICAO` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                     `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                     `DATA_INSERCAO` datetime NOT NULL,
                                     `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                     `DATA_ALTERACAO` datetime DEFAULT NULL,
                                     PRIMARY KEY (`ID_PERMISSAO_SISTEMA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.resposta definition

CREATE TABLE IF NOT EXISTS `dochub`.`resposta` (
                            `ID_RESPOSTA` int NOT NULL AUTO_INCREMENT,
                            `DESCRICAO` varchar(256) DEFAULT NULL,
                            `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `DATA_INSERCAO` datetime NOT NULL,
                            `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `DATA_ALTERACAO` datetime DEFAULT NULL,
                            PRIMARY KEY (`ID_RESPOSTA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.servico definition

CREATE TABLE IF NOT EXISTS `dochub`.`servico` (
                           `ID_SERVICO` int NOT NULL AUTO_INCREMENT,
                           `DESCRICAO` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `DATA_INSERCAO` datetime NOT NULL,
                           `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `DATA_ALTERACAO` datetime DEFAULT NULL,
                           PRIMARY KEY (`ID_SERVICO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.usuario definition


CREATE TABLE IF NOT EXISTS `dochub`.`usuario` (
                           `ID_USUARIO` int NOT NULL AUTO_INCREMENT,
                           `NOME` varchar(256) NOT NULL,
                           `SENHA` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `EMAIL` varchar(128) NOT NULL,
                           `USERNAME` varchar(256) NOT NULL,
                           `AVATAR` longblob,
                           `DATA_ULTIMO_ACESSO` datetime DEFAULT NULL,
                           `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `DATA_INSERCAO` datetime NOT NULL,
                           `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `DATA_ALTERACAO` datetime DEFAULT NULL,
                           PRIMARY KEY (`ID_USUARIO`),
                           UNIQUE KEY `usuario_email_unique` (`EMAIL`),
                           UNIQUE KEY `usuario_username_unique` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.auditoria_recuperacao_senha definition

CREATE TABLE IF NOT EXISTS`auditoria_recuperacao_senha` (
                                               `TOKEN` varchar(256) NOT NULL,
                                               `ID_USUARIO` int NOT NULL,
                                               `DATA_EXPIRACAO` datetime NOT NULL,
                                               `STATUS` enum('Inválido','Não Utilizado','Utilizado') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                               PRIMARY KEY (`TOKEN`),
                                               KEY `auditoria_redifinicao_senha_usuario_FK` (`ID_USUARIO`),
                                               CONSTRAINT `auditoria_redifinicao_senha_usuario_FK` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuario` (`ID_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.cargo_permissao_grupo definition

CREATE TABLE IF NOT EXISTS`cargo_permissao_grupo` (
                                         `ID_CARGO` int NOT NULL,
                                         `ID_PERMISSAO_GRUPO` int NOT NULL,
                                         `ID_GRUPO` int NOT NULL,
                                         `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                         `DATA_INSERCAO` datetime NOT NULL,
                                         `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                         `DATA_ALTERACAO` datetime DEFAULT NULL,
                                         PRIMARY KEY (`ID_PERMISSAO_GRUPO`,`ID_CARGO`,`ID_GRUPO`),
                                         KEY `cargo_permissao_grupo_cargo_FK` (`ID_CARGO`),
                                         KEY `cargo_permissao_grupo_grupo_FK` (`ID_GRUPO`),
                                         CONSTRAINT `cargo_permissao_grupo_cargo_FK` FOREIGN KEY (`ID_CARGO`) REFERENCES `cargo` (`ID_CARGO`),
                                         CONSTRAINT `cargo_permissao_grupo_grupo_FK` FOREIGN KEY (`ID_GRUPO`) REFERENCES `grupo` (`ID_GRUPO`),
                                         CONSTRAINT `cargo_permissao_grupo_permissao_grupo_FK` FOREIGN KEY (`ID_PERMISSAO_GRUPO`) REFERENCES `permissao_grupo` (`ID_PERMISSAO_GRUPO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.cargo_permissao_sistema definition

CREATE TABLE IF NOT EXISTS `dochub`.`cargo_permissao_sistema` (
                                           `ID_CARGO` int NOT NULL,
                                           `ID_PERMISSAO_SISTEMA` int NOT NULL,
                                           `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                           `DATA_INSERCAO` datetime NOT NULL,
                                           `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                           `DATA_ALTERACAO` datetime DEFAULT NULL,
                                           PRIMARY KEY (`ID_CARGO`,`ID_PERMISSAO_SISTEMA`),
                                           KEY `CARGO_PERMISSAO_SISTEMA_permissao_sistema_FK` (`ID_PERMISSAO_SISTEMA`),
                                           CONSTRAINT `CARGO_PERMISSAO_SISTEMA_cargo_FK` FOREIGN KEY (`ID_CARGO`) REFERENCES `cargo` (`ID_CARGO`),
                                           CONSTRAINT `CARGO_PERMISSAO_SISTEMA_permissao_sistema_FK` FOREIGN KEY (`ID_PERMISSAO_SISTEMA`) REFERENCES `permissao_sistema` (`ID_PERMISSAO_SISTEMA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.historico definition

CREATE TABLE IF NOT EXISTS `dochub`.`historico` (
                             `ID_HISTORICO` int NOT NULL AUTO_INCREMENT,
                             `ID_GRUPO` int NOT NULL,
                             `TIPO_MOVIMENTACAO` enum('CRIADO','EDITADO','DELETADO') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `DESCRICAO` varchar(256) DEFAULT NULL,
                             `USUARIO_MOVIMENTACAO` varchar(100) NOT NULL,
                             `DATA_MOVIMENTACAO` datetime NOT NULL,
                             PRIMARY KEY (`ID_HISTORICO`),
                             KEY `historico_grupo_FK` (`ID_GRUPO`),
                             CONSTRAINT `historico_grupo_FK` FOREIGN KEY (`ID_GRUPO`) REFERENCES `grupo` (`ID_GRUPO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.processo definition

CREATE TABLE IF NOT EXISTS `dochub`.`processo` (
                            `ID_PROCESSO` int NOT NULL AUTO_INCREMENT,
                            `DATA_INICIO` date NOT NULL,
                            `DATA_FIM` date DEFAULT NULL,
                            `ID_SERVICO` int NOT NULL,
                            `ID_GRUPO` int NOT NULL,
                            `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `DATA_INSERCAO` datetime NOT NULL,
                            `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `DATA_ALTERACAO` datetime DEFAULT NULL,
                            PRIMARY KEY (`ID_PROCESSO`),
                            KEY `processo_servico_FK` (`ID_SERVICO`),
                            KEY `processo_grupo_FK` (`ID_GRUPO`),
                            CONSTRAINT `processo_grupo_FK` FOREIGN KEY (`ID_GRUPO`) REFERENCES `grupo` (`ID_GRUPO`),
                            CONSTRAINT `processo_servico_FK` FOREIGN KEY (`ID_SERVICO`) REFERENCES `servico` (`ID_SERVICO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.recurso definition

CREATE TABLE IF NOT EXISTS `dochub`.`recurso` (
                           `ID_RECURSO` int NOT NULL AUTO_INCREMENT,
                           `NOME` varchar(128) NOT NULL,
                           `DESCRICAO` varchar(256) DEFAULT NULL,
                           `ID_GRUPO` int NOT NULL,
                           `ORIGEM` enum('GRUPO','FLUXO') NOT NULL,
                           `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `DATA_INSERCAO` datetime NOT NULL,
                           `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `DATA_ALTERACAO` datetime DEFAULT NULL,
                           PRIMARY KEY (`ID_RECURSO`),
                           KEY `recurso_grupo_FK` (`ID_GRUPO`),
                           CONSTRAINT `recurso_grupo_FK` FOREIGN KEY (`ID_GRUPO`) REFERENCES `grupo` (`ID_GRUPO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.solicitacao definition

CREATE TABLE IF NOT EXISTS `dochub`.`solicitacao` (
                               `ID_SOLICITACAO` int NOT NULL AUTO_INCREMENT,
                               `ID_USUARIO` int NOT NULL,
                               `ID_PROCESSO` int NOT NULL,
                               `STATUS` enum('EM ANDAMENTO','FINALIZADO') NOT NULL,
                               `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                               `DATA_INSERCAO` datetime NOT NULL,
                               `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                               `DATA_ALTERACAO` datetime DEFAULT NULL,
                               PRIMARY KEY (`ID_SOLICITACAO`),
                               KEY `solicitacao_usuario_FK` (`ID_USUARIO`),
                               KEY `solicitacao_processo_FK` (`ID_PROCESSO`),
                               CONSTRAINT `solicitacao_processo_FK` FOREIGN KEY (`ID_PROCESSO`) REFERENCES `processo` (`ID_PROCESSO`),
                               CONSTRAINT `solicitacao_usuario_FK` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuario` (`ID_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.usuario_cargo definition

CREATE TABLE IF NOT EXISTS `dochub`.`usuario_cargo` (
                                 `ID_USUARIO` int NOT NULL,
                                 `ID_CARGO` int NOT NULL,
                                 `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                 `DATA_INSERCAO` datetime NOT NULL,
                                 `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `DATA_ALTERACAO` datetime DEFAULT NULL,
                                 PRIMARY KEY (`ID_USUARIO`,`ID_CARGO`),
                                 KEY `usuario_cargo_cargo_FK` (`ID_CARGO`),
                                 CONSTRAINT `usuario_cargo_cargo_FK` FOREIGN KEY (`ID_CARGO`) REFERENCES `cargo` (`ID_CARGO`),
                                 CONSTRAINT `usuario_cargo_usuario_FK` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuario` (`ID_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.cargo_permissao_recurso definition

CREATE TABLE IF NOT EXISTS `cargo_permissao_recurso` (
                                           `ID_CARGO` int NOT NULL,
                                           `ID_PERMISSAO_RECURSO` int NOT NULL,
                                           `ID_RECURSO` int NOT NULL,
                                           `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                           `DATA_INSERCAO` datetime NOT NULL,
                                           `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                           `DATA_ALTERACAO` datetime DEFAULT NULL,
                                           PRIMARY KEY (`ID_CARGO`,`ID_PERMISSAO_RECURSO`,`ID_RECURSO`),
                                           KEY `cargo_permissao_recurso_permissao_recurso_FK` (`ID_PERMISSAO_RECURSO`),
                                           KEY `cargo_permissao_recurso_recurso_FK` (`ID_RECURSO`),
                                           CONSTRAINT `cargo_permissao_recurso_cargo_FK` FOREIGN KEY (`ID_CARGO`) REFERENCES `cargo` (`ID_CARGO`),
                                           CONSTRAINT `cargo_permissao_recurso_permissao_recurso_FK` FOREIGN KEY (`ID_PERMISSAO_RECURSO`) REFERENCES `permissao_recurso` (`ID_PERMISSAO_RECURSO`),
                                           CONSTRAINT `cargo_permissao_recurso_recurso_FK` FOREIGN KEY (`ID_RECURSO`) REFERENCES `recurso` (`ID_RECURSO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.fluxo definition

CREATE TABLE IF NOT EXISTS `dochub`.`fluxo` (
                         `ID_FLUXO` int NOT NULL AUTO_INCREMENT,
                         `ORDEM` int NOT NULL,
                         `PRAZO` int DEFAULT NULL COMMENT 'Prazo em dias para o fluxo obter uma resposta.',
                         `DATA_LIMITE` date DEFAULT NULL COMMENT 'Data específica para aquele fluxo sem respondido.',
                         `ID_PROCESSO` int NOT NULL,
                         `ID_ATIVIDADE` int NOT NULL,
                         `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `DATA_INSERCAO` datetime NOT NULL,
                         `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `DATA_ALTERACAO` datetime DEFAULT NULL,
                         PRIMARY KEY (`ID_FLUXO`),
                         KEY `fluxo_processo_FK` (`ID_PROCESSO`),
                         KEY `fluxo_atividade_FK` (`ID_ATIVIDADE`),
                         CONSTRAINT `fluxo_atividade_FK` FOREIGN KEY (`ID_ATIVIDADE`) REFERENCES `atividade` (`ID_ATIVIDADE`),
                         CONSTRAINT `fluxo_processo_FK` FOREIGN KEY (`ID_PROCESSO`) REFERENCES `processo` (`ID_PROCESSO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.fluxo_resposta definition

CREATE TABLE IF NOT EXISTS `dochub`.`fluxo_resposta` (
                                  `ID_FLUXO` int NOT NULL,
                                  `ID_RESPOSTA` int NOT NULL,
                                  `ID_FLUXO_DESTINO` int DEFAULT NULL,
                                  `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `DATA_INSERCAO` datetime NOT NULL,
                                  `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                  `DATA_ALTERACAO` datetime DEFAULT NULL,
                                  PRIMARY KEY (`ID_FLUXO`,`ID_RESPOSTA`),
                                  KEY `fluxo_resposta_fluxo_fk` (`ID_FLUXO_DESTINO`),
                                  KEY `fluxo_resposta_resposta_fk` (`ID_RESPOSTA`),
                                  CONSTRAINT `fluxo_resposta_fluxo_fk` FOREIGN KEY (`ID_FLUXO_DESTINO`) REFERENCES `fluxo` (`ID_FLUXO`),
                                  CONSTRAINT `fluxo_resposta_fluxo_fk_1` FOREIGN KEY (`ID_FLUXO`) REFERENCES `fluxo` (`ID_FLUXO`),
                                  CONSTRAINT `fluxo_resposta_resposta_fk` FOREIGN KEY (`ID_RESPOSTA`) REFERENCES `resposta` (`ID_RESPOSTA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.movimento definition

CREATE TABLE IF NOT EXISTS `dochub`.`movimento` (
                             `ID_MOVIMENTO` int NOT NULL AUTO_INCREMENT,
                             `ID_SOLICITACAO` int NOT NULL,
                             `ID_FLUXO` int NOT NULL,
                             `ID_RESPOSTA` int NOT NULL,
                             `ORDEM` int NOT NULL,
                             `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `DATA_INSERCAO` datetime NOT NULL,
                             `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                             `DATA_ALTERACAO` datetime DEFAULT NULL,
                             PRIMARY KEY (`ID_MOVIMENTO`),
                             KEY `movimento_fluxo_resposta_FK` (`ID_FLUXO`,`ID_RESPOSTA`),
                             KEY `movimento_solicitacao_FK` (`ID_SOLICITACAO`),
                             CONSTRAINT `movimento_fluxo_resposta_FK` FOREIGN KEY (`ID_FLUXO`, `ID_RESPOSTA`) REFERENCES `fluxo_resposta` (`ID_FLUXO`, `ID_RESPOSTA`),
                             CONSTRAINT `movimento_solicitacao_FK` FOREIGN KEY (`ID_SOLICITACAO`) REFERENCES `solicitacao` (`ID_SOLICITACAO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.movimento_recurso definition

CREATE TABLE IF NOT EXISTS `dochub`.`movimento_recurso` (
                                     `ID_MOVIMENTO` int NOT NULL,
                                     `ID_RECURSO` int NOT NULL,
                                     `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                     `DATA_INSERCAO` datetime NOT NULL,
                                     `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                     `DATA_ALTERACAO` datetime DEFAULT NULL,
                                     PRIMARY KEY (`ID_MOVIMENTO`,`ID_RECURSO`),
                                     KEY `movimento_recurso_recurso_FK` (`ID_RECURSO`),
                                     CONSTRAINT `movimento_recurso_movimento_FK` FOREIGN KEY (`ID_MOVIMENTO`) REFERENCES `movimento` (`ID_MOVIMENTO`),
                                     CONSTRAINT `movimento_recurso_recurso_FK` FOREIGN KEY (`ID_RECURSO`) REFERENCES `recurso` (`ID_RECURSO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.pasta definition

CREATE TABLE IF NOT EXISTS `dochub`.`pasta` (
                         `ID_PASTA` int NOT NULL,
                         `ID_PASTA_PAI` int DEFAULT NULL,
                         `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `DATA_INSERCAO` datetime NOT NULL,
                         `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `DATA_ALTERACAO` datetime DEFAULT NULL,
                         PRIMARY KEY (`ID_PASTA`),
                         KEY `pasta_pai_recurso_FK` (`ID_PASTA_PAI`),
                         CONSTRAINT `pasta_pai_recurso_FK` FOREIGN KEY (`ID_PASTA_PAI`) REFERENCES `recurso` (`ID_RECURSO`) ON DELETE CASCADE ON UPDATE RESTRICT,
                         CONSTRAINT `pasta_recurso_FK` FOREIGN KEY (`ID_PASTA`) REFERENCES `recurso` (`ID_RECURSO`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.usuario_fluxo definition

CREATE TABLE IF NOT EXISTS `dochub`.`usuario_fluxo` (
                                 `ID_USUARIO` int NOT NULL,
                                 `ID_FLUXO` int NOT NULL,
                                 `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                 `DATA_INSERCAO` datetime NOT NULL,
                                 `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `DATA_ALTERACAO` datetime DEFAULT NULL,
                                 PRIMARY KEY (`ID_USUARIO`,`ID_FLUXO`),
                                 KEY `usuario_fluxo_fluxo_FK` (`ID_FLUXO`),
                                 CONSTRAINT `usuario_fluxo_fluxo_FK` FOREIGN KEY (`ID_FLUXO`) REFERENCES `fluxo` (`ID_FLUXO`),
                                 CONSTRAINT `usuario_fluxo_usuario_FK` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuario` (`ID_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- dochub.arquivo definition

CREATE TABLE IF NOT EXISTS `dochub`.`arquivo` (
                           `ID_ARQUIVO` int NOT NULL,
                           `ID_S3_OBJECT` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `TIPO` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `TAMANHO` bigint NOT NULL,
                           `ID_PASTA` int DEFAULT NULL,
                           `USUARIO_INSERCAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `DATA_INSERCAO` datetime NOT NULL,
                           `USUARIO_ALTERACAO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `DATA_ALTERACAO` datetime DEFAULT NULL,
                           PRIMARY KEY (`ID_ARQUIVO`),
                           KEY `arquivo_pasta_FK` (`ID_PASTA`),
                           CONSTRAINT `arquivo_pasta_FK` FOREIGN KEY (`ID_PASTA`) REFERENCES `pasta` (`ID_PASTA`) ON DELETE CASCADE ON UPDATE RESTRICT,
                           CONSTRAINT `arquivo_recurso_FK` FOREIGN KEY (`ID_ARQUIVO`) REFERENCES `recurso` (`ID_RECURSO`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;