package com.example.family_budget.controller;

import com.example.family_budget.entity.Transaction;
import com.example.family_budget.service.AccountService;
import com.example.family_budget.service.TransactionService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BudgetController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/balance")
    public BigDecimal getBalance() {
        return accountService.getBalance();
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequest request) {
        try {
            Transaction transaction = transactionService.addTransaction(
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
        return transactionService.getUserTransactions(userName);
    }
}

@Getter
@Setter
class TransactionRequest {
    private String userName;
    private BigDecimal amount;
    private String type;
    private String description;
}
