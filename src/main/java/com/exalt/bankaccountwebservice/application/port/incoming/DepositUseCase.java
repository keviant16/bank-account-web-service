package com.exalt.bankaccountwebservice.application.port.incoming;

import com.exalt.bankaccountwebservice.application.model.request.OperationRequest;

import java.math.BigDecimal;

public interface DepositUseCase {
    void deposit(Long accountId, OperationRequest request);
}
