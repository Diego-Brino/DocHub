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
    String LOGIN_PAGE_URL = "http://localhost:8085/login";
    String AVATAR_URL = "http://localhost:8080/users/%s/avatar";
    String GROUP_URL = "http://localhost:8080/groups/%s/avatar";
    String PASSWORD_RECOVERY_LINK = "http://localhost:8085/password-recovery?token=%s";

    // Email Subjects
    String PASSWORD_RESET_INITIATOR_NAME = "DocHub - ResetLink";

    // S3
    Integer UUID_BUCKET_CHARACTER_LIMIT = 49;
    String BUCKET_NAME = "dochub-%s-dochub";

    // Utilities
    String ADMIN = "ADMINISTRADOR";
    String UTF_8 = "UTF-8";
    String PASSWORD_RECOVERY_TITLE_EMAIL = "Redefina sua senha do DocHub";
    String ACCOUNT_CREATION_TITLE_EMAIL = "Bem-vindo(a) à DocHub";
    String ROOT = "Raiz";
    String RESOURCE_CREATED_HISTORY_MESSAGE = "Um novo recurso chamado '%s' foi criado na pasta '%s'.";
    String RESOURCE_NAME_UPDATED_MESSAGE = "O nome do recurso foi alterado de '%s' para '%s'.";
    String RESOURCE_DESCRIPTION_UPDATED_MESSAGE = "A descrição do recurso foi alterada de '%s' para '%s'.";
    String RESOURCE_FOLDER_UPDATED_MESSAGE = "O recurso foi movido da pasta '%s' para a pasta '%s'.";
    String RESOURCE_CONTENT_TYPE_UPDATED_MESSAGE = "O tipo do recurso foi alterado de '%s' para '%s'.";
    String RESOURCE_LENGTH_UPDATED_MESSAGE = "O tamanho do recurso foi alterado de '%s' para '%s'.";
    String RESOURCE_DELETED_HISTORY_MESSAGE = "O recurso '%s' foi deletado da pasta '%s'.";

    // Templates
    String PASSWORD_RECOVERY_EMAIL_TEMPLATE_PATH = "templates/password_recovery.html";
    String ACCOUNT_CREATION_EMAIL_TEMPLATE_PATH = "templates/account-creation.html";

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

    // GroupPermissionService Permissions
    String VIEW_GROUP = "Visualizar Grupo";

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

    // Profile Permissions
    String CREATE_USER_PERMISSION = "Criar Usuário";
    String UPDATE_USER_PROFILE_PERMISSION = "Atualizar perfil de Usuário";
    String DELETE_USER_PERMISSION = "Deletar Usuário";

    // ResourceHistoryService Permissions
    String VIEW_RESOURCES_HISTORY = "Visualizar histórico de Recursos";

    // ServiceService Permissions
    String CREATE_SERVICE = "Criar Serviço";
    String EDIT_SERVICE = "Editar Serviço";
    String DELETE_SERVICE = "Deletar Serviço";

    // Activity Permissions
    String CREATE_ACTIVITY = "Criar Atividade";
    String EDIT_ACTIVITY = "Editar Atividade";
    String DELETE_ACTIVITY = "Deletar Atividade";

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
    String CONTENT_TYPE_IS_REQUIRED_MESSAGE = "O campo 'Content-Type' é obrigatório.";
    String HASH_S3_IS_REQUIRED_MESSAGE = "O campo 'hashS3' é obrigatório.";
    String DESCRIPTION_IS_REQUIRED_MESSAGE = "O campo 'Descrição' é obrigatório.";

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
    String CANNOT_DELETE_OWN_USER_EXCEPTION  = "Não é permitido deletar o próprio usuário.";
    String CANNOT_CREATE_ADMIN_ROLE_EXCEPTION  = "Não é possível criar um cargo com o nome 'ADMINISTRADOR', pois esse cargo já existe e é reservado pelo sistema.";
    String CANNOT_DELETE_ADMIN_ROLE_EXCEPTION  = "O cargo 'ADMINISTRADOR' não pode ser excluído, pois é um cargo protegido do sistema.";
    String CANNOT_EDIT_ADMIN_ROLE_EXCEPTION  = "O cargo 'ADMINISTRADOR' não pode ser modificado, pois é um cargo protegido do sistema.";
    String INVALID_FILE_TYPE_EXCEPTION  = "O arquivo deve ser do tipo JPG, JPEG ou PNG.";
    String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE = "Nenhuma entidade foi encontrada com os parâmetros fornecidos.";
    String INPUT_STREM_READ_EXCEPTION_MESSAGE = "Não foi possível ler o conteúdo do arquivo.";
    String INVALID_FOLDER_MOVE_EXCEPTION_MESSAGE = "Uma pasta não pode ser movida para ela mesma.";
    String ARCHIVE_ALREADY_EXISTS_EXCEPTION_MESSAGE = "Já existe um arquivo apontando para o s3Hash informado.";
    String CANNOT_DELETE_SERVICE_EXCEPTION_MESSAGE = "Não é possível deletar o serviço informado, existem processos vinculados a ele.";
    String CANNOT_DELETE_ACTIVITY_EXCEPTION_MESSAGE = "Não é possível deletar a atividade selecionada, existem fluxos vinculadas a ela.";

    // S3 Exception Messages
    String BUCKET_ALREADY_EXISTS_EXCEPTION_MESSAGE = "O bucket '%s' já existe!";
    String BUCKET_NOT_FOUND_EXCEPTION = "Nenhum bucket foi encontrado com o nome '%s'.";
    String CREATE_BUCKET_EXCEPTION_MESSAGE = "Ocorreu um erro inesperado ao tentar criar o bucket '%s'.";
    String DELETE_BUCKET_EXCEPTION_MESSAGE = "Ocorreu um erro inesperado ao tentar deletar o bucket '%s'.";
    String OBJECT_NOT_FOUND_EXCEPTION = "O arquivo '%s' não foi encontrado no bucket '%s'.";
    String DELETE_OBJECT_EXCEPTION_MESSAGE = "Ocorreu um erro inesperado ao tentar deletar o arquivo '%s' no bucket '%s'.";
    String GET_S3_OBJECT_EXCEPTION_MESSAGE = "Ocorreu um erro inesperado ao tentar recuperar o arquivo '%s' no bucket '%s'.";
    String PRESIGNED_URL_GENERATION_EXCEPTION_MESSAGE = "Falha ao gerar a URL pré-assinada para o arquivo especificado.";
}