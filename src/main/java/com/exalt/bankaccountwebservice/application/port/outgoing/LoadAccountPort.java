package com.exalt.bankaccountwebservice.application.port.outgoing;

import com.exalt.bankaccountwebservice.application.domain.ClientBankAccount;

import java.util.Optional;

public interface LoadAccountPort {
    Optional<ClientBankAccount> load(Long id);
}
