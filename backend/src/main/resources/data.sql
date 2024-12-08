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
TRUNCATE TABLE `dochub`.`usuario_fluxo`;
TRUNCATE TABLE `dochub`.`arquivo`;
TRUNCATE TABLE `dochub`.`solicitacao`;
TRUNCATE TABLE `dochub`.`movimento`;
TRUNCATE TABLE `dochub`.`movimento_recurso`;

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
   ('Mover Arquivo', 'DocHub', CURRENT_DATE),
   ('Deletar Arquivo', 'DocHub', CURRENT_DATE),
   ('Criar Pasta', 'DocHub', CURRENT_DATE),
   ('Mover Pasta', 'DocHub', CURRENT_DATE),
   ('Deletar Pasta', 'DocHub', CURRENT_DATE),
   ('Criar Usuário', 'DocHub', CURRENT_DATE),
   ('Atualizar perfil de Usuário', 'DocHub', CURRENT_DATE),
   ('Deletar Usuário', 'DocHub', CURRENT_DATE),
   ('Visualizar histórico de Recursos', 'DocHub', CURRENT_DATE),
   ('Criar Serviço', 'DocHub', CURRENT_DATE),
   ('Editar Serviço', 'DocHub', CURRENT_DATE),
   ('Deletar Serviço', 'DocHub', CURRENT_DATE),
   ('Criar Atividade', 'DocHub', CURRENT_DATE),
   ('Editar Atividade', 'DocHub', CURRENT_DATE),
   ('Deletar Atividade', 'DocHub', CURRENT_DATE),
   ('Criar Resposta', 'DocHub', CURRENT_DATE),
   ('Editar Resposta', 'DocHub', CURRENT_DATE),
   ('Deletar Resposta', 'DocHub', CURRENT_DATE),
   ('Criar Processo', 'DocHub', CURRENT_DATE),
   ('Editar Processo', 'DocHub', CURRENT_DATE),
   ('Deletar Processo', 'DocHub', CURRENT_DATE),
   ('Criar Fluxo', 'DocHub', CURRENT_DATE),
   ('Editar Fluxo', 'DocHub', CURRENT_DATE),
   ('Deletar Fluxo', 'DocHub', CURRENT_DATE),
   ('Criar Fluxo de Resposta', 'DocHub', CURRENT_DATE),
   ('Editar Fluxo de Resposta', 'DocHub', CURRENT_DATE),
   ('Deletar Fluxo de Resposta', 'DocHub', CURRENT_DATE),
   ('Vincular Usuário ao Fluxo', 'DocHub', CURRENT_DATE),
   ('Desvincular Usuário de Fluxo', 'DocHub', CURRENT_DATE),
   ('Solicitar Processo', 'DocHub', CURRENT_DATE);

INSERT INTO permissao_grupo (DESCRICAO, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    ('Visualizar Grupo', 'DocHub', CURRENT_DATE),
    ('Editar Grupo', 'DocHub', CURRENT_DATE),
    ('Deletar Grupo', 'DocHub', CURRENT_DATE),
    ('Criar Arquivo', 'DocHub', CURRENT_DATE),
    ('Mover Arquivo', 'DocHub', CURRENT_DATE),
    ('Deletar Arquivo', 'DocHub', CURRENT_DATE),
    ('Criar Pasta', 'DocHub', CURRENT_DATE),
    ('Mover Pasta', 'DocHub', CURRENT_DATE),
    ('Deletar Pasta', 'DocHub', CURRENT_DATE),
    ('Visualizar histórico de Recursos', 'DocHub', CURRENT_DATE);

INSERT INTO permissao_recurso (DESCRICAO, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    ('Visualizar Arquivo', 'DocHub', CURRENT_DATE),
    ('Mover Arquivo', 'DocHub', CURRENT_DATE),
    ('Deletar Arquivo', 'DocHub', CURRENT_DATE),
    ('Visualizar Pasta', 'DocHub', CURRENT_DATE),
    ('Mover Pasta', 'DocHub', CURRENT_DATE),
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
    (1, 23, 'DocHub', CURRENT_DATE),
    (1, 24, 'DocHub', CURRENT_DATE),
    (1, 25, 'DocHub', CURRENT_DATE),
    (1, 26, 'DocHub', CURRENT_DATE),
    (1, 27, 'DocHub', CURRENT_DATE),
    (1, 28, 'DocHub', CURRENT_DATE),
    (1, 29, 'DocHub', CURRENT_DATE),
    (1, 30, 'DocHub', CURRENT_DATE),
    (1, 31, 'DocHub', CURRENT_DATE),
    (1, 32, 'DocHub', CURRENT_DATE),
    (1, 33, 'DocHub', CURRENT_DATE),
    (1, 34, 'DocHub', CURRENT_DATE),
    (1, 35, 'DocHub', CURRENT_DATE),
    (1, 36, 'DocHub', CURRENT_DATE),
    (1, 37, 'DocHub', CURRENT_DATE),
    (1, 38, 'DocHub', CURRENT_DATE),
    (1, 39, 'DocHub', CURRENT_DATE),
    (1, 40, 'DocHub', CURRENT_DATE),
    (1, 41, 'DocHub', CURRENT_DATE),
    (1, 42, 'DocHub', CURRENT_DATE),
    (1, 43, 'DocHub', CURRENT_DATE),
    (1, 44, 'DocHub', CURRENT_DATE),
    (1, 45, 'DocHub', CURRENT_DATE);

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
    (1, 9, 1,'DocHub', CURRENT_DATE),
    (1, 10, 1,'DocHub', CURRENT_DATE),
    (1, 1, 2, 'DocHub', CURRENT_DATE),
    (1, 2, 2,'DocHub', CURRENT_DATE),
    (1, 3, 2,'DocHub', CURRENT_DATE),
    (1, 4, 2,'DocHub', CURRENT_DATE),
    (1, 5, 2,'DocHub', CURRENT_DATE),
    (1, 6, 2,'DocHub', CURRENT_DATE),
    (1, 7, 2,'DocHub', CURRENT_DATE),
    (1, 8, 2,'DocHub', CURRENT_DATE),
    (1, 9, 2,'DocHub', CURRENT_DATE),
    (1, 10, 2,'DocHub', CURRENT_DATE),
    (1, 1, 3, 'DocHub', CURRENT_DATE),
    (1, 2, 3,'DocHub', CURRENT_DATE),
    (1, 3, 3,'DocHub', CURRENT_DATE),
    (1, 4, 3,'DocHub', CURRENT_DATE),
    (1, 5, 3,'DocHub', CURRENT_DATE),
    (1, 6, 3,'DocHub', CURRENT_DATE),
    (1, 7, 3,'DocHub', CURRENT_DATE),
    (1, 8, 3,'DocHub', CURRENT_DATE),
    (1, 9, 3,'DocHub', CURRENT_DATE),
    (1, 10, 3,'DocHub', CURRENT_DATE);

INSERT INTO recurso (NOME, DESCRICAO, ID_GRUPO, ORIGEM, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    ('PDF', 'Documento PDF na Raiz', 1, 'GRUPO', 'DocHub', CURRENT_DATE),
    ('DOCX', 'Documento DOCX na Raiz', 1, 'GRUPO','DocHub', CURRENT_DATE),
    ('Logo Unaerp', 'Logo da Unaerp na Raiz', 2, 'GRUPO', 'DocHub', CURRENT_DATE),
    ('PDF', 'Documento PDF na Raiz', 3, 'GRUPO', 'DocHub', CURRENT_DATE);

INSERT INTO arquivo (ID_ARQUIVO, ID_S3_OBJECT, TIPO, TAMANHO, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    (1, 'teste.pdf', 'application/pdf', 30.3, 'DocHub', CURRENT_DATE),
    (2, 'teste.docx', 'application/pdf', 11.7, 'DocHub', CURRENT_DATE),
    (3, 'Logos png-04.png', 'image/png', 65, 'DocHub', CURRENT_DATE),
    (4, 'teste.pdf', 'application/pdf', 30.3, 'DocHub', CURRENT_DATE);