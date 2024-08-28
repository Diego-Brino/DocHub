
DELETE FROM auditoria_recuperacao_senha;

DELETE FROM usuario;
INSERT INTO usuario (NOME, SENHA, EMAIL, USERNAME, AVATAR, DATA_ULTIMO_ACESSO, USUARIO_INSERCAO, DATA_INSERCAO, USUARIO_ALTERACAO, DATA_ALTERACAO)
VALUES
    ('João Silva', '$2a$10$zSgELT6bMGCegYxsMrkITeUGUkanQfWA1TvX.8POzxaM1c54vx4jK', 'joaosilva@exemplo.com', 'joao_silva', NULL, NULL, 'DocHub', '2024-08-21 11:01:10', NULL, NULL),
    ('Diego Manucci Bizzotto', '$2a$10$zSgELT6bMGCegYxsMrkITeUGUkanQfWA1TvX.8POzxaM1c54vx4jK', 'diego.bizzotto@sou.unaerp.edu.br', 'dbizzotto', NULL, NULL, 'DocHub', '2024-08-21 11:01:10', NULL, NULL),
    ('Diego Simonaio Brino', '$2a$10$zSgELT6bMGCegYxsMrkITeUGUkanQfWA1TvX.8POzxaM1c54vx4jK', 'diego.brino@sou.unaerp.edu.br', 'dbrino', NULL, NULL, 'DocHub', '2024-08-21 11:01:10', NULL, NULL);