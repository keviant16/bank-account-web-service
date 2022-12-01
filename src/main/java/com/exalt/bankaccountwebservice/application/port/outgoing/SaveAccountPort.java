package com.exalt.bankaccountwebservice.application.port.outgoing;

import com.exalt.bankaccountwebservice.application.domain.ClientBankAccount;

public interface SaveAccountPort {
    ClientBankAccount save(ClientBankAccount account);
}
