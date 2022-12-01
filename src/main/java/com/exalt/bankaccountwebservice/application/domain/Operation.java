package com.exalt.bankaccountwebservice.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Operation_GEN")
    @SequenceGenerator(name = "Operation_GEN", sequenceName = "Operation_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;
    private BigDecimal amount;
    private EOperation operation;
    @CreationTimestamp
    private LocalDate creationDate;
}
