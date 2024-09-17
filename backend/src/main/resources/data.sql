DELETE FROM auditoria_recuperacao_senha;
DELETE FROM usuario;
DELETE FROM permissao_sistema;
DELETE FROM cargo;
DELETE FROM cargo_permissao_sistema;
DELETE FROM usuario_cargo;

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
   ('Desvincular permissão de sistema de Cargo', 'DocHub', CURRENT_DATE);

INSERT INTO cargo (NOME, DESCRICAO, COR, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    ('ADMINISTRADOR', 'ADMINISTRADOR DO SISTEMA', '#FAFAFA', 'DocHub', CURRENT_DATE);

INSERT INTO cargo_permissao_sistema (ID_CARGO, ID_PERMISSAO_SISTEMA, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    (1, 1, 'DocHub', CURRENT_DATE),
    (1, 2, 'DocHub', CURRENT_DATE),
    (1, 3, 'DocHub', CURRENT_DATE),
    (1, 4, 'DocHub', CURRENT_DATE),
    (1, 5, 'DocHub', CURRENT_DATE),
    (1, 6, 'DocHub', CURRENT_DATE),
    (1, 7, 'DocHub', CURRENT_DATE);

INSERT INTO usuario_cargo (ID_USUARIO, ID_CARGO, USUARIO_INSERCAO, DATA_INSERCAO)
VALUES
    (2, 1, 'DocHub', CURRENT_DATE),
    (3, 1, 'DocHub', CURRENT_DATE);