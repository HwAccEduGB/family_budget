package com.example.family_budget.service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface AccountService {
    @Transactional
    BigDecimal getBalance();

    @Transactional
    void addFunds(BigDecimal amount);

    @Transactional
    boolean withdrawFunds(BigDecimal amount);


}
