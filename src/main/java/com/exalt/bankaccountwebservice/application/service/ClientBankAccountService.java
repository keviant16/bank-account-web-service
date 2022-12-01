package com.exalt.bankaccountwebservice.application.service;

import com.exalt.bankaccountwebservice.application.domain.ClientBankAccount;
import com.exalt.bankaccountwebservice.application.domain.EOperation;
import com.exalt.bankaccountwebservice.application.domain.Operation;
import com.exalt.bankaccountwebservice.application.port.incoming.*;
import com.exalt.bankaccountwebservice.application.port.outgoing.CheckEmailPort;
import com.exalt.bankaccountwebservice.application.port.outgoing.LoadAccountPort;
import com.exalt.bankaccountwebservice.application.port.outgoing.SaveAccountPort;
import com.exalt.bankaccountwebservice.application.port.outgoing.SendEmailPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBankAccountService implements DepositUseCase, WithdrawUseCase, ViewHistoryUseCase, OpenAccountUseCase, ViewAccountUseCase {
    private LoadAccountPort loadAccountPort;
    private SaveAccountPort saveAccountPort;
    private CheckEmailPort checkEmailPort;

    private SendEmailPort sendEmailPort;

    public ClientBankAccountService(LoadAccountPort loadAccountPort, SaveAccountPort saveAccountPort, CheckEmailPort checkEmailPort, SendEmailPort sendEmailPort) {
        this.loadAccountPort = loadAccountPort;
        this.saveAccountPort = saveAccountPort;
        this.checkEmailPort = checkEmailPort;
        this.sendEmailPort = sendEmailPort;
    }

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        ClientBankAccount account = loadAccountPort.load(accountId).orElseThrow(NoSuchElementException::new);
        boolean hasDeposit = account.deposit(amount);

        if(!hasDeposit) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Negative number are not allowed !");
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
        boolean emailExist = checkEmailPort.check(account.getEmail());
        if (emailExist) throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exist !");

        account.generateCodePin();

        boolean emailHasBeenSend = sendEmailPort.sendEmail(account.getEmail(), account.getFirstName(), account.getLastName(), account.getCodePin());
        if (!emailHasBeenSend) throw new ResponseStatusException(HttpStatus.valueOf(500), "Email as not been send");
        //TODO: encode password


        account.setBalance(BigDecimal.valueOf(0));
        account.setOperations(new ArrayList<>());
        return saveAccountPort.save(account);
    }

    @Override
    public ClientBankAccount viewAccount(Long id) {
        return loadAccountPort.load(id).orElseThrow(NoSuchElementException::new);
    }
}
