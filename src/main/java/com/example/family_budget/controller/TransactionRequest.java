package com.example.family_budget.controller;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
class TransactionRequest {
    private String userName;
    private BigDecimal amount;
    private String type;
    private String description;
}
