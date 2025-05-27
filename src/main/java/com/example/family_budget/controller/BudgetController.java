package com.example.family_budget.controller;

import com.example.family_budget.dto.ResponseModel;
import com.example.family_budget.dto.TransactionRequest;
import com.example.family_budget.entity.Transaction;
import com.example.family_budget.entity.User;
import com.example.family_budget.repository.UserRepository;
import com.example.family_budget.service.UserAuthService;
import com.example.family_budget.service.impl.AccountServiceImpl;
import com.example.family_budget.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BudgetController {
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/balance")
    public BigDecimal getBalance(@RequestHeader("X-User-Id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return accountServiceImpl.getBalance();
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> addTransaction(@RequestHeader("X-User-Id") Long userId,
                                            @RequestBody TransactionRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Transaction transaction = transactionServiceImpl.addTransaction(
                user.getName(),
                request.getAmount(),
                request.getType(),
                request.getDescription());
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/transactions/{userName}")
    public List<Transaction> getUserTransactions(@PathVariable String userName) {
        return transactionServiceImpl.getUserTransactions(userName);
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionServiceImpl.getAllTransactions();
    }

    @PostMapping("/auth/google")
    public ResponseEntity<ResponseModel> authenticateWithGoogle(@RequestBody Map<String, String> body) {
        String idToken = body.get("token");
        if (idToken == null || idToken.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseModel("error", 0));
        }
        System.out.println("Received token: " + idToken);
        System.out.println("Token length: " + idToken.length());

        try {
            ResponseModel response = userAuthService.authenticateWithGoogle(idToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error during Google authentication: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseModel("error", 0));
        }
    }
}


