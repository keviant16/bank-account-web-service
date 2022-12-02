package com.exalt.bankaccountwebservice.application.port.incoming;

import com.exalt.bankaccountwebservice.application.model.request.OperationRequest;

import java.math.BigDecimal;

public interface WithdrawUseCase {
    void withdraw(Long accountId, OperationRequest request);
}
