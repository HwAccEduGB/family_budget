package com.example.family_budget.service;

import com.example.family_budget.entity.Account;
import com.example.family_budget.entity.User;
import com.example.family_budget.entity.Transaction;
import com.example.family_budget.repository.AccountRepository;
import com.example.family_budget.repository.UserRepository;
import com.example.family_budget.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public Transaction addTransaction(String userName, BigDecimal amount, String type, String description) {
        User user = userRepository.findByName(userName).orElseGet(() -> {
            User newUser = new User();
            newUser.setName(userName);
            return userRepository.save(newUser);
        });

        boolean success = true;
        if ("withdrawal".equalsIgnoreCase(type)) {
            success = accountService.withdrawFunds(amount);
        } else if ("deposit".equalsIgnoreCase(type)) {
            accountService.addFunds(amount);
        }

        if (!success && "withdrawal".equalsIgnoreCase(type)) {
            throw new RuntimeException("Недостаточно средств");
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDate(LocalDateTime.now());
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getUserTransactions(String userName) {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return transactionRepository.findByUser(user);
    }
}
