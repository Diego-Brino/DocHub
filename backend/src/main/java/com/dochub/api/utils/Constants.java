package com.dochub.api.utils;

public interface Constants {
    String SYSTEM_NAME = "DocHub";

    String SECRET_KEY = "oSFhWKDttuPQudTwQeU6bIhgPBKo63WK3HfsUJzol6VFHfNHi2JcPv6WEr3WgtHKuJz539Z0howHyDvi6mkPhsKCc2/bqPiMo50gUDxgsSBYO+eFQtmUh2o4D8Nx3l0R/SC26iRMwZ4rk/M+Cx7JN+2uWF0QSRI+UbDA9XzCXlI5QfV0r+zLNnayqzeBiaowqqKdSCgIfmnOwOpeRlWpZCY6sVNTXGFzXCn2kGUOm9T6DbtwbkkilPFjQefi4povQj7xwavePDrxZSMhPUC0F3tQqjoAJ7RHR47UxKa25UJUJx3bn9T4sZ3H0TsZMiroLPUbo5jW+3EaL9ShMqKv+9gMT8ESdRl5RePjO9NxsO0=";

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