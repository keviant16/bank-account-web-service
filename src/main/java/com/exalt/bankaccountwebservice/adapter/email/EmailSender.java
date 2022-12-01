package com.exalt.bankaccountwebservice.adapter.email;

import com.exalt.bankaccountwebservice.application.port.outgoing.SendEmailPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender implements SendEmailPort {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean sendEmail(String email, String firstName, String lastName, int codePin) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(email);
            mailMessage.setText("Bonjour " + firstName + " " + lastName + ",\nVoici le code pin de votre compte bancaire exalt : " + codePin + ".\n Il va vous permettre de confirmer votre identiter lors de la réalisation d'opérations");
            mailMessage.setSubject("Code pin Exalt Bank Account");

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
