package com.exalt.bankaccountwebservice.adapter.persistence;

import com.exalt.bankaccountwebservice.application.domain.ClientBankAccount;
import com.exalt.bankaccountwebservice.application.port.outgoing.CheckEmailPort;
import com.exalt.bankaccountwebservice.application.port.outgoing.LoadAccountPort;
import com.exalt.bankaccountwebservice.application.port.outgoing.SaveAccountPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientBankAccountRepository implements SaveAccountPort, LoadAccountPort, CheckEmailPort {
    private SpringDataAccountRepository repository;

    public ClientBankAccountRepository(SpringDataAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientBankAccount save(ClientBankAccount account) {
        return repository.save(account);
    }

    @Override
    public Optional<ClientBankAccount> load(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean check(String email) {
        return repository.existsByEmail(email);
    }
}
