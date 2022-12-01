package com.exalt.bankaccountwebservice.application.port.incoming;

import java.math.BigDecimal;

public interface DepositUseCase {
    void deposit(Long accountId, BigDecimal amount);
}
