SET foreign_key_checks = 0;

TRUNCATE TABLE `dochub`.`resposta`;
TRUNCATE TABLE `dochub`.`servico`;
TRUNCATE TABLE `dochub`.`permissao_recurso`;
TRUNCATE TABLE `dochub`.`permissao_sistema`;
TRUNCATE TABLE `dochub`.`permissao_grupo`;
TRUNCATE TABLE `dochub`.`atividade`;
TRUNCATE TABLE `dochub`.`cargo`;
TRUNCATE TABLE `dochub`.`grupo`;
TRUNCATE TABLE `dochub`.`cargo_permissao_grupo`;
TRUNCATE TABLE `dochub`.`cargo_permissao_sistema`;
TRUNCATE TABLE `dochub`.`atividade`;
TRUNCATE TABLE `dochub`.`usuario`;
TRUNCATE TABLE `dochub`.`processo`;
TRUNCATE TABLE `dochub`.`recurso`;
TRUNCATE TABLE `dochub`.`usuario_cargo`;
TRUNCATE TABLE `dochub`.`auditoria_recuperacao_senha`;
TRUNCATE TABLE `dochub`.`cargo_permissao_recurso`;
TRUNCATE TABLE `dochub`.`fluxo`;
TRUNCATE TABLE `dochub`.`fluxo_resposta`;
TRUNCATE TABLE `dochub`.`historico`;
TRUNCATE TABLE `dochub`.`pasta`;
TRUNCATE TABLE `dochub`.`usuario_cargo_fluxo`;
TRUNCATE TABLE `dochub`.`arquivo`;
TRUNCATE TABLE `dochub`.`processo_recurso`;

SET foreign_key_checks = 1;

INSERT INTO usuario (NOME, SENHA, EMAIL, USERNAME, AVATAR, DATA_ULTIMO_ACESSO, USUARIO_INSERCAO, DATA_INSERCAO, USUARIO_ALTERACAO, DATA_ALTERACAO)
VALUES
    ('João Silva', '$2a$10$zSgELT6bMGCegYxsMrkITeUGUkanQfWA1TvX.8POzxaM1c54vx4jK', 'joaosilva@exemplo.com', 'joao_silva', NULL, NULL, 'DocHub', '2024-08-21 11:01:10', NULL, NULL),
    ('Diego Manucci Bizzotto', '$2a$10$zSgELT6bMGCegYxsMrkITeUGUkanQfWA1TvX.8POzxaM1c54vx4jK', 'diego.bizzotto@sou.unaerp.edu.br', 'dbizzotto', NULL, NULL, 'DocHub', '2024-08-21 11:01:10', NULL, NULL),
    ('Diego Simonaio Brino', '$2a$10$zSgELT6bMGCegYxsMrkITeUGUkanQfWA1TvX.8POzxaM1c54vx4jK', 'diego.brino@sou.unaerp.edu.br', 'dbrino', NULL, NULL, 'DocHub', '2024-08-21 11:01:10', NULL, NULL);

INSERT INTO permissao_sistema (DESCRICAO, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
   ('Criar Cargo', 'DocHub', CURRENT_DATE),
   ('Editar Cargo', 'DocHub', CURRENT_DATE),
   ('Deletar Cargo', 'DocHub', CURRENT_DATE),
   ('Vincular Usuário á Cargo', 'DocHub', CURRENT_DATE),
   ('Desvincular Usuário de Cargo', 'DocHub', CURRENT_DATE),
   ('Vincular permissão de sistema á Cargo', 'DocHub', CURRENT_DATE),
   ('Desvincular permissão de sistema de Cargo', 'DocHub', CURRENT_DATE),
   ('Vincular permissão de grupo á Cargo', 'DocHub', CURRENT_DATE),
   ('Desvincular permissão de grupo de Cargo', 'DocHub', CURRENT_DATE),
   ('Criar Grupo', 'DocHub', CURRENT_DATE),
   ('Editar Grupo', 'DocHub', CURRENT_DATE),
   ('Deletar Grupo', 'DocHub', CURRENT_DATE),
   ('Vincular permissão de recurso á Cargo', 'DocHub', CURRENT_DATE),
   ('Desvincular permissão de recurso de Cargo', 'DocHub', CURRENT_DATE),
   ('Criar Arquivo', 'DocHub', CURRENT_DATE),
   ('Editar Arquivo', 'DocHub', CURRENT_DATE),
   ('Deletar Arquivo', 'DocHub', CURRENT_DATE),
   ('Criar Pasta', 'DocHub', CURRENT_DATE),
   ('Editar Pasta', 'DocHub', CURRENT_DATE),
   ('Deletar Pasta', 'DocHub', CURRENT_DATE),
   ('Criar Usuário', 'DocHub', CURRENT_DATE),
   ('Atualizar perfil de Usuário', 'DocHub', CURRENT_DATE),
   ('Deletar Usuário', 'DocHub', CURRENT_DATE);

INSERT INTO permissao_grupo (DESCRICAO, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    ('Visualizar Grupo', 'DocHub', CURRENT_DATE),
    ('Editar Grupo', 'DocHub', CURRENT_DATE),
    ('Deletar Grupo', 'DocHub', CURRENT_DATE),
    ('Criar Arquivo', 'DocHub', CURRENT_DATE),
    ('Editar Arquivo', 'DocHub', CURRENT_DATE),
    ('Deletar Arquivo', 'DocHub', CURRENT_DATE),
    ('Criar Pasta', 'DocHub', CURRENT_DATE),
    ('Editar Pasta', 'DocHub', CURRENT_DATE),
    ('Deletar Pasta', 'DocHub', CURRENT_DATE);

INSERT INTO permissao_recurso (DESCRICAO, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    ('Editar Arquivo', 'DocHub', CURRENT_DATE),
    ('Deletar Arquivo', 'DocHub', CURRENT_DATE),
    ('Editar Pasta', 'DocHub', CURRENT_DATE),
    ('Deletar Pasta', 'DocHub', CURRENT_DATE);

INSERT INTO cargo (NOME, DESCRICAO, COR, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    ('ADMINISTRADOR', 'ADMINISTRADOR DO SISTEMA', '#FAFAFA', 'DocHub', CURRENT_DATE),
    ('CARGO 1', 'CARGO 1', '#eb4034', 'DocHub', CURRENT_DATE),
    ('CARGO 2', 'CARGO 2', '#b4eb34', 'DocHub', CURRENT_DATE),
    ('CARGO 3', 'CARGO 3', '#34e5eb', 'DocHub', CURRENT_DATE),
    ('CARGO 4', 'CARGO 4', '#c034eb', 'DocHub', CURRENT_DATE);

INSERT INTO cargo_permissao_sistema (ID_CARGO, ID_PERMISSAO_SISTEMA, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    (1, 1, 'DocHub', CURRENT_DATE),
    (1, 2, 'DocHub', CURRENT_DATE),
    (1, 3, 'DocHub', CURRENT_DATE),
    (1, 4, 'DocHub', CURRENT_DATE),
    (1, 5, 'DocHub', CURRENT_DATE),
    (1, 6, 'DocHub', CURRENT_DATE),
    (1, 7, 'DocHub', CURRENT_DATE),
    (1, 8, 'DocHub', CURRENT_DATE),
    (1, 9, 'DocHub', CURRENT_DATE),
    (1, 10, 'DocHub', CURRENT_DATE),
    (1, 11, 'DocHub', CURRENT_DATE),
    (1, 12, 'DocHub', CURRENT_DATE),
    (1, 13, 'DocHub', CURRENT_DATE),
    (1, 14, 'DocHub', CURRENT_DATE),
    (1, 15, 'DocHub', CURRENT_DATE),
    (1, 16, 'DocHub', CURRENT_DATE),
    (1, 17, 'DocHub', CURRENT_DATE),
    (1, 18, 'DocHub', CURRENT_DATE),
    (1, 19, 'DocHub', CURRENT_DATE),
    (1, 20, 'DocHub', CURRENT_DATE),
    (1, 21, 'DocHub', CURRENT_DATE),
    (1, 22, 'DocHub', CURRENT_DATE),
    (1, 23, 'DocHub', CURRENT_DATE);

INSERT INTO usuario_cargo (ID_USUARIO, ID_CARGO, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    (2, 1, 'DocHub', CURRENT_DATE),
    (3, 1, 'DocHub', CURRENT_DATE);

INSERT INTO grupo (ID_S3_BUCKET, NOME, DESCRICAO, AVATAR, USUARIO_INSERCAO, DATA_INSERCAO, USUARIO_ALTERACAO, DATA_ALTERACAO)
VALUES
    ('dochub-group1-dochub', 'Grupo A', 'Descrição do Grupo A', NULL, 'DocHub', CURRENT_DATE, NULL, NULL),
    ('dochub-group2-dochub', 'Grupo B', 'Descrição do Grupo B', NULL, 'DocHub', CURRENT_DATE, NULL, NULL),
    ('dochub-group3-dochub', 'Grupo C', NULL, NULL, 'DocHub', CURRENT_DATE, NULL, NULL);

INSERT INTO cargo_permissao_grupo (ID_CARGO, ID_PERMISSAO_GRUPO, ID_GRUPO, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    (1, 1, 1, 'DocHub', CURRENT_DATE),
    (1, 2, 1,'DocHub', CURRENT_DATE),
    (1, 3, 1,'DocHub', CURRENT_DATE),
    (1, 4, 1,'DocHub', CURRENT_DATE),
    (1, 5, 1,'DocHub', CURRENT_DATE),
    (1, 6, 1,'DocHub', CURRENT_DATE),
    (1, 7, 1,'DocHub', CURRENT_DATE),
    (1, 8, 1,'DocHub', CURRENT_DATE),
    (1, 9, 1,'DocHub', CURRENT_DATE);
