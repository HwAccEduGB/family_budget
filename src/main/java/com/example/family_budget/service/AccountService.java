package com.example.family_budget.service;

import com.example.family_budget.entity.Account;
import com.example.family_budget.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account getAccount() {
        // Предположим, что у нас один счет, получим его или создадим при необходимости
        return accountRepository.findById(1L).orElseGet(() -> {
            Account newAccount = new Account();
            newAccount.setBalance(0.0);
            return accountRepository.save(newAccount);
        });
    }

    @Transactional
    public void updateBalance(Double amount) {
        Account account = getAccount();
        Double newBalance = account.getBalance() + amount;
        if (newBalance < 0) {
            throw new RuntimeException("Недостаточно средств");
        }
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
}
