package com.exalt.bankaccountwebservice.application.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {

    private ClientBankAccount account;
    private BigDecimal amount;

    @BeforeEach
    void setUp() {
        amount = BigDecimal.valueOf(50);
        account = new ClientBankAccount(
                1L,
                "foo",
                "bar",
                "foobar@email.com",
                "password",
                1234,
                BigDecimal.valueOf(0),
                LocalDate.now(),
                new ArrayList<>()
        );
    }

    @Test
    void givenAmount_whenDeposit_thenExpectEqualToBalance() {
        account.deposit(amount);
        assertEquals(amount, account.getBalance());
    }

    @Test
    void givenAmount_whenDeposit_thenReturnTrue() {
        assertEquals(true, account.deposit(amount));
    }

    @Test
    void test_deposit_is_not_ok() {
        amount = BigDecimal.valueOf(-50);
        assertEquals(false, account.deposit(amount));

    }

    @Test
    void test_withdraw_is_ok() {
        account.deposit(amount);
        assertEquals(true, account.withdraw(amount));
    }

    @Test
    void test_withdraw_is_not_ok() {
        assertEquals(false, account.withdraw(amount));
    }

    @Test
    void test_add_operation() {
        account.addOperation(amount, EOperation.DEPOSIT);
        assertEquals(1, account.getOperations().size());
    }

    @Test
    void test_add_operation_type() {
        account.addOperation(amount, EOperation.WITHDRAW);
        EOperation operationType = account.getOperations().get(0).getOperation();
        assertEquals(EOperation.WITHDRAW, operationType);
    }

    @Test
    void test_getHistory() {
        account.addOperation(amount, EOperation.DEPOSIT);
        account.addOperation(amount, EOperation.DEPOSIT);
        account.addOperation(amount, EOperation.DEPOSIT);

        assertEquals(3, account.getOperations().size());
    }

    @Test
    void test_pin_is_ok() {
        assertEquals(true, account.isPinValid(1234));
    }

    @Test
    void test_pin_is_not_ok() {
        assertEquals(false,account.isPinValid(7894));
    }

    @Test
    void test_generate_code_pin(){
        account.generateCodePin();
        assertTrue(account.getCodePin() > 999);
    }
}
