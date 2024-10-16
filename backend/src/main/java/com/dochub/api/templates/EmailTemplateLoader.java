package com.dochub.api.templates;

import com.dochub.api.utils.Constants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Component
public class EmailTemplateLoader {
    public String loadResetPasswordTemplate (final String recoveryLink, final String name) {
        try {
            final ClassPathResource resource = new ClassPathResource(Constants.PASSWORD_RECOVERY_EMAIL_TEMPLATE_PATH);
            final String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

            return content
                .replace("{{recoveryLink}}", recoveryLink)
                .replace("{{name}}", name)
                .replace("{{year}}", String.valueOf(LocalDate.now().getYear()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o template de e-mail", e);
        }
    }

    public String loadAccountCreationTemplate (final String name, final String email, final String password) {
        try {
            final ClassPathResource resource = new ClassPathResource(Constants.ACCOUNT_CREATION_EMAIL_TEMPLATE_PATH);
            final String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

            return content
                .replace("{{loginPage}}", Constants.LOGIN_PAGE_URL)
                .replace("{{name}}", name)
                .replace("{{email}}", email)
                .replace("{{password}}", password)
                .replace("{{year}}", String.valueOf(LocalDate.now().getYear()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o template de criação de conta", e);
        }
    }
}