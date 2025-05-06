package com.example.family_budget.controller;

import com.example.family_budget.entity.Transaction;
import com.example.family_budget.service.impl.AccountServiceImpl;
import com.example.family_budget.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BudgetController {
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @GetMapping("/balance")
    public BigDecimal getBalance() {
        return accountServiceImpl.getBalance();
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequest request) {
        try {
            Transaction transaction = transactionServiceImpl.addTransaction(
                    request.getUserName(),
                    request.getAmount(),
                    request.getType(),
                    request.getDescription()
            );
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/transactions/{userName}")
    public List<Transaction> getUserTransactions(@PathVariable String userName) {
        return transactionServiceImpl.getUserTransactions(userName);
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionServiceImpl.getAllTransactions();
    }
}


