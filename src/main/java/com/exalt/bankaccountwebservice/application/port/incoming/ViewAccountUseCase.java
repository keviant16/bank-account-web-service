package com.exalt.bankaccountwebservice.application.port.incoming;

import com.exalt.bankaccountwebservice.application.domain.ClientBankAccount;

public interface ViewAccountUseCase {
    ClientBankAccount viewAccount(Long id);
}
