package com.exalt.bankaccountwebservice.application.port.incoming;

import java.math.BigDecimal;

public interface WithdrawUseCase {
    void withdraw(Long accountId, BigDecimal amount);
}
