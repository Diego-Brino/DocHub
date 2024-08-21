package com.dochub.api.utils;

public interface Constants {
    // Token Expiration Times
    long TOKEN_EXPIRATION_TIME_MILLIS = 1000 * 60 * 20; // 20 minutes
    long RECOVER_PASSWORD_TOKEN_EXPIRATION_TIME_MILLIS = 24 * 60 * 60 * 1000; // 24 hours

    // System Information
    String SYSTEM_NAME = "DocHub";

    // HTTP Headers
    String AUTHORIZATION_HEADER = "Authorization";

    // Parameters
    String AVATAR_PARAMETER = "avatar";

    // File Paths
    String USER_ICON_PATH = "images/UserIcon.png";

    // URLs
    String AVATAR_URL = "http://localhost:8080/user/%s/avatar";
    String PASSWORD_RESET_LINK = "http://localhost:8085/reset-password?token=%s";

    // Email Subjects
    String PASSWORD_RESET_INITIATOR_NAME = "DocHub - ResetLink";

    // Validation Messages
    String NAME_IS_REQUIRED_MESSAGE = "O campo 'Nome' é obrigatório.";
    String PASSWORD_IS_REQUIRED_MESSAGE = "O campo 'Senha' é obrigatório.";
    String EMAIL_IS_REQUIRED_MESSAGE = "O campo 'E-mail' é obrigatório.";
    String INVALID_EMAIL_MESSAGE = "O endereço de e-mail fornecido é inválido.";
    String USERNAME_IS_REQUIRED_MESSAGE = "O campo 'Nome de Usuário' é obrigatório.";
    String AUTHENTICATION_TOKEN_IS_REQUIRED_MESSAGE = "O token de autenticação é obrigatório.";
    String COLOR_IS_REQUIRED_MESSAGE = "O campo 'Cor' é obrigatório.";
    String OLD_PASSWORD_IS_REQUIRED_MESSAGE = "O campo 'Senha Anterior' é obrigatório.";
    String NEW_PASSWORD_IS_REQUIRED_MESSAGE = "O campo 'Nova Senha' é obrigatório.";

    // Exception Messages
    String GENERIC_ERROR_EXCEPTION_MESSAGE = "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.";
    String ENTITY_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE = "Nenhuma entidade foi encontrada com o ID fornecido.";
    String USER_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE = "Nenhum usuário foi encontrado com o endereço de e-mail fornecido.";
    String EMAIL_ALREADY_REGISTERED_EXCEPTION_MESSAGE = "O endereço de e-mail fornecido já está em uso.";
    String USERNAME_ALREADY_REGISTERED_EXCEPTION_MESSAGE = "O nome de usuário fornecido já está em uso.";
    String INVALID_CREDENTIALS_EXCEPTION_MESSAGE = "Nome de usuário ou senha inválidos.";
    String MULTIPART_FILE_READ_EXCEPTION_MESSAGE = "Não foi possível ler o conteúdo do arquivo.";
    String INPUT_STREAM_READ_EXCEPTION_MESSAGE = "Não foi possível ler o conteúdo do fluxo de entrada.";
    String INVALID_TOKEN_FORMAT_EXCEPTION_MESSAGE = "O formato do token fornecido é inválido.";
    String EXPIRED_TOKEN_EXCEPTION_MESSAGE = "O token fornecido expirou.";
    String INVALID_PASSWORD_RECOVERY_TOKEN_EXCEPTION_MESSAGE = "O token de redefinição de senha foi invalidado.";
    String USED_PASSWORD_RECOVERY_TOKEN_EXCEPTION_MESSAGE = "O token de redefinição de senha já foi utilizado.";
    String PASSWORD_RECOVERY_TOKEN_NOT_FOUND_EXCEPTION_MESSAGE = "Não foi possível localizar o token de redefinição de senha.";
    String PASSWORD_MISMATCH_EXCEPTION_MESSAGE = "A senha fornecida não corresponde a senha registrada.";
    String INVALID_USERNAME_FORMAT_EXCEPTION_MESSAGE = "O campo 'Username' não pode conter espaços em branco ou caracteres especiais.";
}