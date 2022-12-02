package com.exalt.bankaccountwebservice.adapter.web;

import com.exalt.bankaccountwebservice.application.model.domain.ClientBankAccount;
import com.exalt.bankaccountwebservice.application.model.domain.Operation;
import com.exalt.bankaccountwebservice.application.model.request.OperationRequest;
import com.exalt.bankaccountwebservice.application.port.incoming.*;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class ClientBankAccountController {

    private final DepositUseCase depositUseCase;

    private final OpenAccountUseCase openAccountUseCase;

    private final ViewHistoryUseCase viewHistoryUseCase;

    private final ViewAccountUseCase viewAccountUseCase;

    private final WithdrawUseCase withdrawUseCase;

    public ClientBankAccountController(DepositUseCase depositUseCase, OpenAccountUseCase openAccountUseCase, ViewHistoryUseCase viewHistoryUseCase, ViewAccountUseCase viewAccountUseCase, WithdrawUseCase withdrawUseCase) {
        this.depositUseCase = depositUseCase;
        this.openAccountUseCase = openAccountUseCase;
        this.viewHistoryUseCase = viewHistoryUseCase;
        this.viewAccountUseCase = viewAccountUseCase;
        this.withdrawUseCase = withdrawUseCase;
    }

    @PostMapping(value = "/open")
    public String open(@RequestBody ClientBankAccount account){
        return openAccountUseCase.open(account);
    }

    @PutMapping(value = "/{id}/deposit")
    public void deposit(@PathVariable Long id, @RequestBody OperationRequest request){
         depositUseCase.deposit(id, request);
    }

    @PutMapping(value = "/{id}/withdraw")
    public void withdraw(@PathVariable Long id, OperationRequest request){
        withdrawUseCase.withdraw(id, request);
    }

    @GetMapping(value = "/{id}/operations")
    public List<Operation> viewHistory(@PathVariable Long id){
        return viewHistoryUseCase.view(id);
    }

    @GetMapping(value = "/{id}")
    public ClientBankAccount viewAccount(@PathVariable Long id){
        return viewAccountUseCase.viewAccount(id);
    }
}
