package com.example.family_budget.service;

import com.example.family_budget.entity.Transaction;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Transaction addTransaction(String userName, BigDecimal amount, String type, String description);
    List<Transaction> getUserTransactions(String userName);
    List<Transaction> getAllTransactions();
}
