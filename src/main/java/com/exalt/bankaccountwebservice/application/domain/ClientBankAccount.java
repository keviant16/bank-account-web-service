package com.exalt.bankaccountwebservice.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientBankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ClientBankAccount_GEN")
    @SequenceGenerator(name = "ClientBankAccount_GEN", sequenceName = "ClientBankAccount_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int codePin;
    private BigDecimal balance;

    @CreationTimestamp
    private LocalDate creationDate;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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
        if (codePin == inputPin) return true;
        return false;
    }

    public void generateCodePin(){
        codePin = (int) (Math.random() * (9999 - 1000 + 1) + 1000 );
    }
}
