package com.exalt.bankaccountwebservice.adapter.persistence;

import com.exalt.bankaccountwebservice.application.domain.ClientBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface SpringDataAccountRepository extends JpaRepository<ClientBankAccount,Long> {

    boolean existsByEmail(String email);
}
