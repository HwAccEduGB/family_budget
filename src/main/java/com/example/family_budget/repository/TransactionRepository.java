package com.example.family_budget.repository;

import com.example.family_budget.entity.Transaction;
import com.example.family_budget.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
