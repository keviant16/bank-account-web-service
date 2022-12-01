package com.exalt.bankaccountwebservice.adapter.configuration;

import com.exalt.bankaccountwebservice.BankAccountWebServiceApplication;
import com.exalt.bankaccountwebservice.adapter.email.EmailSender;
import com.exalt.bankaccountwebservice.adapter.persistence.ClientBankAccountRepository;
import com.exalt.bankaccountwebservice.application.service.ClientBankAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = BankAccountWebServiceApplication.class)
public class BeanConfiguration {

    @Bean
    ClientBankAccountService bankAccountService(ClientBankAccountRepository repository, EmailSender emailSender){
        return new ClientBankAccountService(repository, repository, repository, emailSender);
    }
}
