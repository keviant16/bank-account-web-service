package com.exalt.bankaccountwebservice.application.service;

import com.exalt.bankaccountwebservice.application.domain.ClientBankAccount;
import com.exalt.bankaccountwebservice.application.domain.EOperation;
import com.exalt.bankaccountwebservice.application.domain.Operation;
import com.exalt.bankaccountwebservice.application.port.incoming.DepositUseCase;
import com.exalt.bankaccountwebservice.application.port.incoming.OpenAccountUseCase;
import com.exalt.bankaccountwebservice.application.port.incoming.ViewHistoryUseCase;
import com.exalt.bankaccountwebservice.application.port.incoming.WithdrawUseCase;
import com.exalt.bankaccountwebservice.application.port.outgoing.LoadAccountPort;
import com.exalt.bankaccountwebservice.application.port.outgoing.SaveAccountPort;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class ClientBankAccountService implements DepositUseCase, WithdrawUseCase, ViewHistoryUseCase, OpenAccountUseCase {

    private LoadAccountPort loadAccountPort;
    private SaveAccountPort saveAccountPort;

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        ClientBankAccount account = loadAccountPort.load(accountId).orElseThrow(NoSuchElementException::new);
        boolean hasDeposit = account.deposit(amount);

        if(!hasDeposit) return;
        account.addOperation(amount, EOperation.DEPOSIT);
        saveAccountPort.save(account);
    }

    @Override
    public List<Operation> view(Long accountId) {
        ClientBankAccount account = loadAccountPort.load(accountId).orElseThrow(NoSuchElementException::new);
        return account.getOperations();
    }

    @Override
    public void withdraw(Long accountId, BigDecimal amount) {
        ClientBankAccount account = loadAccountPort.load(accountId).orElseThrow(NoSuchElementException::new);
        boolean hasDeposit = account.withdraw(amount);

        if(!hasDeposit) return;
        account.addOperation(amount, EOperation.WITHDRAW);
        saveAccountPort.save(account);
    }

    @Override
    public ClientBankAccount open(ClientBankAccount account) {
        return saveAccountPort.save(account);
    }
}
