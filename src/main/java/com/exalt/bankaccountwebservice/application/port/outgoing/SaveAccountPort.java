package com.exalt.bankaccountwebservice.application.port.outgoing;

import com.exalt.bankaccountwebservice.application.model.domain.ClientBankAccount;

public interface SaveAccountPort {
    ClientBankAccount save(ClientBankAccount account);
}
