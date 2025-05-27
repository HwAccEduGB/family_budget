package com.example.family_budget.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {
    private String userName;
    private BigDecimal amount;
    private String type;
    private String description;
}
