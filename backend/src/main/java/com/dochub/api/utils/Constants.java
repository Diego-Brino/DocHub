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
    String GROUP_ICON_PATH = "images/GroupIcon.png";

    // URLs
    String AVATAR_URL = "http://localhost:8080/users/%s/avatar";
    String GROUP_URL = "http://localhost:8080/groups/%s/avatar";
    String PASSWORD_RECOVERY_LINK = "http://localhost:8085/password-recovery?token=%s";

    // Email Subjects
    String PASSWORD_RESET_INITIATOR_NAME = "DocHub - ResetLink";

    // RoleService Permissions
    String CREATE_ROLE_PERMISSION = "Criar Cargo";
    String EDIT_ROLE_PERMISSION = "Editar Cargo";
    String DELETE_ROLE_PERMISSION = "Deletar Cargo";

    // UserRoleService Permissions
    String CREATE_USER_ROLE_PERMISSION = "Vincular Usuário á Cargo";
    String DELETE_USER_ROLE_PERMISSION = "Desvincular Usuário de Cargo";

    // SystemRolePermissionService Permissions
    String CREATE_SYSTEM_ROLE_PERMISSION = "Vincular permissão de sistema á Cargo";
    String DELETE_SYSTEM_ROLE_PERMISSION = "Desvincular permissão de sistema de Cargo";

    // GroupRolePermissionService Permissions
    String CREATE_GROUP_ROLE_PERMISSION = "Vincular permissão de grupo á Cargo";
    String DELETE_GROUP_ROLE_PERMISSION = "Desvincular permissão de grupo de Cargo";

    // GroupService Permissions
    String CREATE_GROUP_PERMISSION = "Criar Grupo";
    String EDIT_GROUP_PERMISSION = "Editar Grupo";
    String DELETE_GROUP_PERMISSION = "Deletar Grupo";

    // ResourceRolePermissionService Permissions
    String CREATE_RESOURCE_ROLE_PERMISSION = "Vincular permissão de recurso á Cargo";
    String DELETE_RESOURCE_ROLE_PERMISSION = "Desvincular permissão de recurso de Cargo";

    // ArchiveService Permissions
    String CREATE_ARCHIVE_PERMISSION = "Criar Arquivo";
    String EDIT_ARCHIVE_PERMISSION = "Editar Arquivo";
    String DELETE_ARCHIVE_PERMISSION = "Deletar Arquivo";

    // FolderService Permissions
    String CREATE_FOLDER_PERMISSION = "Criar Pasta";
    String EDIT_FOLDER_PERMISSION = "Editar Pasta";
    String DELETE_FOLDER_PERMISSION = "Deletar Pasta";

    // ResourceService Permissions
    String CANNOT_VIEW_RESOURCE_PERMISSION = "Não visualizar Recurso";

    // Profile Permissions
    String UPDATE_USER_PROFILE_PERMISSION = "Atualizar perfil de Usuário";

    // Utilities
    String UTF_8 = "UTF-8";
    String PASSWORD_RECOVERY_TITLE_EMAIL = "Redefina sua senha do DocHub";

    // Templates
    String PASSWORD_RECOVERY_EMAIL_TEMPLATE_PATH = "templates/password_recovery.html";

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
    String ID_USER_IS_REQUIRED_MESSAGE = "O campo 'Id Usuário' é obrigatório.";
    String ID_ROLE_IS_REQUIRED_MESSAGE = "O campo 'Id Cargo' é obrigatório.";
    String ID_SYSTEM_PERMISSION_IS_REQUIRED_MESSAGE = "O campo 'Id Permissão de Sistema' é obrigatório.";
    String ID_GROUP_PERMISSION_IS_REQUIRED_MESSAGE = "O campo 'Id Permissão de Grupo' é obrigatório.";
    String ID_GROUP_IS_REQUIRED_MESSAGE = "O campo 'Id Grupo' é obrigatório.";
    String ID_RESOURCE_PERMISSION_IS_REQUIRED_MESSAGE = "O campo 'Id Permissão de Recurso' é obrigatório.";
    String ID_RESOURCE_IS_REQUIRED_MESSAGE = "O campo 'Id Recurso' é obrigatório.";

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
    String PERMISSION_DENIED_EXCEPTION_MESSAGE = "O usuário em questão não possui permissão para %s.";
    String USER_MISMATCH_EXCEPTION_MESSAGE = "O usuário autenticado não corresponde ao usuário que foi passado como parâmetro.";
    String ROLE_CANNOT_BE_DELETED_EXCEPTION_MESSAGE = "O cargo não pode ser excluído porque existem usuários vinculados a ele.";
    String GROUP_CANNOT_BE_DELETED_EXCEPTION_MESSAGE = "O grupo não pode ser excluído porque existem processos, recursos ou permissões de grupo vinculados a ele.";
}