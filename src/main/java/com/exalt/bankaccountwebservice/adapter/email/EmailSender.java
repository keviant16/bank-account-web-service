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
            mailMessage.setText("Hello " + firstName + " " + lastName + ",\nHere is the pin code of your bank account exalt : " + codePin + ".\nIt will allow you to confirm your identity when carrying out operations.");
            mailMessage.setSubject("Exalt Bank Account Pin Code");

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
