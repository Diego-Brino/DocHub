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
    public String loadResetPasswordTemplate(final String link, final String name) {
        try {
            ClassPathResource resource = new ClassPathResource(Constants.PASSWORD_RECOVERY_EMAIL_TEMPLATE_PATH);
            String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            return content.replace("{{recoveryLink}}", link)
                    .replace("{{name}}", name)
                    .replace("{{year}}", String.valueOf(LocalDate.now().getYear()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o template de e-mail", e);
        }
    }
}