package com.exalt.bankaccountwebservice.application.port.outgoing;

import com.exalt.bankaccountwebservice.application.model.domain.ClientBankAccount;

public interface SendEmailPort {
    boolean sendEmail(ClientBankAccount account);
}
