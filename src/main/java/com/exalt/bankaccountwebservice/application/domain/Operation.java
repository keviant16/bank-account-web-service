package com.exalt.bankaccountwebservice.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Operation {
    private Long id;
    private BigDecimal amount;
    private EOperation operation;
    private LocalDate creationDate;
}
