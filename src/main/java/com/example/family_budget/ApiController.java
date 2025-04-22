package com.example.family_budget;

import com.example.family_budget.entity.Transaction;
import com.example.family_budget.service.AccountService;
import com.example.family_budget.service.TransactionService;
import com.example.family_budget.entity.Client;
import com.example.family_budget.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientRepository clientRepository;

    // Получить текущий баланс
    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance() {
        return ResponseEntity.ok(accountService.getAccount().getBalance());
    }

    // Получить все транзакции
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    // Добавить клиента
    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.ok(savedClient);
    }

    // Пополнить счет
    @PostMapping("/transactions/add")
    public ResponseEntity<String> addFunds(@RequestParam Long clientId, @RequestParam Double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Сумма должна быть положительной");
        }
        transactionService.addTransaction(clientId, amount);
        return ResponseEntity.ok("Пополнение успешно");
    }

    // Снять деньги
    @PostMapping("/transactions/remove")
    public ResponseEntity<String> removeFunds(@RequestParam Long clientId, @RequestParam Double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Сумма должна быть положительной");
        }
        transactionService.addTransaction(clientId, -amount);
        return ResponseEntity.ok("Снятие успешно");
    }
}
