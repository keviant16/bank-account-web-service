package com.exalt.bankaccountwebservice.adapter.web;

import com.exalt.bankaccountwebservice.application.domain.ClientBankAccount;
import com.exalt.bankaccountwebservice.application.domain.Operation;
import com.exalt.bankaccountwebservice.application.port.incoming.DepositUseCase;
import com.exalt.bankaccountwebservice.application.port.incoming.OpenAccountUseCase;
import com.exalt.bankaccountwebservice.application.port.incoming.ViewHistoryUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class ClientBankAccountController {
    private final DepositUseCase depositUseCase;
    private final OpenAccountUseCase openAccountUseCase;
    private final ViewHistoryUseCase viewHistoryUseCase;

    @PostMapping
    public ClientBankAccount open(@RequestBody ClientBankAccount account){
        return openAccountUseCase.open(account);
    }

    @PutMapping(value = "/{id}/deposit/{amount}")
    public void deposit(@PathVariable Long id, @PathVariable BigDecimal amount){
         depositUseCase.deposit(id, amount);
    }

    @PutMapping(value = "/{id}/withdraw/{amount}")
    public void withdraw(@PathVariable Long id, @PathVariable BigDecimal amount){
     depositUseCase.deposit(id, amount);
    }

    @GetMapping(value = "/{id}/operations")
    public List<Operation> viewHistory(@PathVariable Long id){
        return viewHistoryUseCase.view(id);
    }
}
