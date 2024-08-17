package com.dochub.api.utils;

public interface Constants {
    String SYSTEM_NAME = "DocHub";

    String NAME_IS_REQUIRED_MESSAGE = "O campo nome é obrigatório";
    String PASSWORD_IS_REQUIRED_MESSAGE = "O campo senha é obrigatório";
    String EMAIL_IS_REQUIRED_MESSAGE = "O campo email é obrigatório";
    String INVALID_EMAIL_MESSAGE = "O e-mail informado é inválido!";
    String USERNAME_IS_REQUIRED_MESSAGE = "O campo username é obrigatório";
    String AUTHENTICATION_TOKEN_IS_REQUIRED_MESSAGE = "O token de autenticação é obrigatório";

    String GENERIC_ERROR_EXCEPTION_MESSAGE = "Um erro inesperado ocorreu, tente novamente mais tarde!";
    String ENTITY_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE = "A entidade não foi encontrada a partir do e-mail informado!";
    String EMAIL_ALREADY_REGISTER_EXCEPTION_MESSAGE = "O e-mail informado já está sendo utilizado!";
    String BAD_CREDENTIALS_EXCEPTION_MESSAGE = "Usuário ou senha inválidos!";
}