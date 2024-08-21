package com.dochub.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Async
    public void sendPasswordRecoveryMail (final String email, final String recoveryLink) {
        String subject = "Solicitação de Recuperação de Senha";
        String message = "Olá,\n\n" +
                "Recebemos uma solicitação para redefinir a senha da sua conta. Se você solicitou essa alteração, por favor, clique no link abaixo para criar uma nova senha:\n\n" +
                recoveryLink + "\n\n" +
                "Por motivos de segurança, este link é válido por apenas 24 horas. Após esse período, será necessário solicitar um novo link de recuperação.\n\n" +
                "Se você não fez essa solicitação, nenhuma ação é necessária. Sua conta permanece segura, e você pode continuar a usá-la normalmente.\n\n" +
                "Caso tenha qualquer dúvida ou preocupação, entre em contato com nosso suporte ao cliente imediatamente.\n\n" +
                "Atenciosamente,\n" +
                "Equipe de Suporte";

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}