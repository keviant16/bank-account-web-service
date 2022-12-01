package com.exalt.bankaccountwebservice.application.port.incoming;

import com.exalt.bankaccountwebservice.application.domain.Operation;

import java.util.List;

public interface ViewHistoryUseCase {
    List<Operation> view(Long account);
}
