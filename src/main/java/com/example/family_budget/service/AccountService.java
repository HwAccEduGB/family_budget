package com.example.family_budget.service;

import com.example.family_budget.entity.Account;
import com.example.family_budget.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public BigDecimal getBalance() {
        Account account = getAccount();
        return account.getBalance();
    }

    @Transactional
    public synchronized void addFunds(BigDecimal amount) {
        Account account = getAccount();
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Transactional
    public synchronized boolean withdrawFunds(BigDecimal amount) {
        Account account = getAccount();
        if (account.getBalance().compareTo(amount) >= 0) {
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    private Account getAccount() {
        return accountRepository.findById(1L).orElseGet(() -> {
            Account newAccount = new Account();
            newAccount.setBalance(BigDecimal.ZERO);
            newAccount.setId(1L);
            return accountRepository.save(newAccount);
        });
    }
}
