package com.exalt.bankaccountwebservice.application.port.outgoing;

public interface SendEmailPort {
    boolean sendEmail(String email, String firstName, String lastName, int codePin);
}
