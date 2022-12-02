package com.exalt.bankaccountwebservice.application.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OperationRequest {
    private BigDecimal amount;
    private int codePin;
}
