package com.exalt.bankaccountwebservice.application.service;

import com.exalt.bankaccountwebservice.application.model.domain.ClientBankAccount;
import com.exalt.bankaccountwebservice.application.model.domain.EOperation;
import com.exalt.bankaccountwebservice.application.model.domain.Operation;
import com.exalt.bankaccountwebservice.application.model.request.OperationRequest;
import com.exalt.bankaccountwebservice.application.port.incoming.*;
import com.exalt.bankaccountwebservice.application.port.outgoing.*;
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
    public void deposit(Long accountId, OperationRequest request) {
        ClientBankAccount account = loadAccountPort.load(accountId).orElseThrow(NoSuchElementException::new);
        boolean hasDeposit = account.deposit(request.getAmount());
        boolean isPinValid = account.isPinValid(request.getCodePin());

        if (!isPinValid) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The code pin is incorrect !");
        if (!hasDeposit) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot deposit negative values !");

        account.addOperation(request.getAmount(), EOperation.DEPOSIT);
        saveAccountPort.save(account);
    }

    @Override
    public List<Operation> view(Long accountId) {
        ClientBankAccount account = loadAccountPort.load(accountId).orElseThrow(NoSuchElementException::new);
        return account.getOperations();
    }

    @Override
    public void withdraw(Long accountId, OperationRequest request) {
        ClientBankAccount account = loadAccountPort.load(accountId).orElseThrow(NoSuchElementException::new);
        boolean hasDeposit = account.withdraw(request.getAmount());
        boolean isPinValid = account.isPinValid(request.getCodePin());

        if (!isPinValid) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The code pin is incorrect !");
        if (!hasDeposit) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can’t withdraw, you don’t have enough money in your balance !");

        account.addOperation(request.getAmount(), EOperation.WITHDRAW);
        saveAccountPort.save(account);
    }

    @Override
    public String open(ClientBankAccount account) {
        boolean emailExist = checkEmailPort.check(account.getEmail());
        if (emailExist) throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exist !");

        account.generateCodePin();
        account.setBalance(BigDecimal.valueOf(0));
        account.setOperations(new ArrayList<>());

        saveAccountPort.save(account);

        boolean emailHasBeenSend = sendEmailPort.sendEmail(account);
        if (!emailHasBeenSend) throw new ResponseStatusException(HttpStatus.valueOf(500), "Email as not been send");

        return "Your account has been create! Check your email to get your pin code.";
    }

    @Override
    public ClientBankAccount viewAccount(Long id) {
        return loadAccountPort.load(id).orElseThrow(NoSuchElementException::new);
    }
}
