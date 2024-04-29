package com.nassaei.project.MockApp.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    private String customerId;
    private String accountType;
    private String accountNo;
    private BigDecimal amount;
}
