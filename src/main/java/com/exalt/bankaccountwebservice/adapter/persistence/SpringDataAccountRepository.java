package com.exalt.bankaccountwebservice.adapter.persistence;

import com.exalt.bankaccountwebservice.application.model.domain.ClientBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface SpringDataAccountRepository extends JpaRepository<ClientBankAccount,Long> {

    boolean existsByEmail(String email);

    Optional<ClientBankAccount> findByEmail(String email);
}
