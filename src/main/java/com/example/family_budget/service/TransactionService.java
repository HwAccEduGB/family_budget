package com.example.family_budget.service;

import com.example.family_budget.entity.Account;
import com.example.family_budget.entity.Client;
import com.example.family_budget.entity.Transaction;
import com.example.family_budget.repository.AccountRepository;
import com.example.family_budget.repository.ClientRepository;
import com.example.family_budget.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional
    public void addTransaction(Long clientId, Double amount) {
        Account account = getAccount();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        Double newBalance = account.getBalance() + amount;
        if (newBalance < 0) {
            throw new RuntimeException("Недостаточно средств");
        }

        // Обновляем баланс
        account.setBalance(newBalance);
        accountRepository.save(account);

        // Создаем транзакцию
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setClient(client);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    // Метод для получения текущего счета
    private Account getAccount() {
        return accountRepository.findById(1L).orElseGet(() -> {
            Account newAccount = new Account();
            newAccount.setBalance(0.0);
            return accountRepository.save(newAccount);
        });
    }
}
