package com.exalt.bankaccountwebservice.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ClientBankAccount {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int pin;
    private BigDecimal balance;
    private LocalDate creationDate;
    private List<Operation> operations;

    public boolean deposit(BigDecimal amount){
        if (amount.intValue() < 0) return false;
        balance = balance.add(amount);
        return true;
    }

    public boolean withdraw(BigDecimal amount){
        if (balance.compareTo(amount) < 0) return false;
        balance = balance.subtract(amount);
        return true;
    }

    public void addOperation(BigDecimal amount, EOperation operationType) {
        Operation newOperation = new Operation(null,amount, operationType, LocalDate.now());
        operations.add(newOperation);
    }

    public boolean isPinValid (int inputPin){
        if (pin == inputPin) return true;
        return false;
    }
}
