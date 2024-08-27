package com.dochub.api.services;

import com.dochub.api.templates.EmailTemplateLoader;
import com.dochub.api.utils.Constants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Async
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final EmailTemplateLoader emailTemplateLoader;

    public void sendPasswordRecoveryMail (final String name, final String email, final String recoveryLink) {
        String content = emailTemplateLoader.loadResetPasswordTemplate(recoveryLink, name);

        _sendHtmlMail(email, Constants.PASSWORD_RECOVERY_TITLE_EMAIL, content);
    }

    private void _sendHtmlMail (String recipientEmail, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, Constants.UTF_8);

            helper.setTo(recipientEmail);
            helper.setSubject(subject);

            // Cria o multipart relacionado
            MimeMultipart multipart = new MimeMultipart("related");

            // Parte HTML
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlContent, "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Adiciona a imagem como parte inline, se necessário
            // Neste caso, se você estiver apenas usando base64 no HTML, você pode pular esta parte.
            // Se precisar de imagens adicionais inline, adicione-as aqui.

            // Define o conteúdo do e-mail
            message.setContent(multipart);

            mailSender.send(message);

            log.info("Email enviado com sucesso para {}", recipientEmail);
        } catch (MessagingException e) {
            log.warn("Falha ao enviar email para {}", recipientEmail);
        }
    }
}