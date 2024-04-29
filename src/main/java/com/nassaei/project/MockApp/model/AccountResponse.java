package com.nassaei.project.MockApp.model;

import lombok.Data;

@Data
public class AccountResponse {
    private String accountNo;
    private String customerId;
    private String status;
    private String message;
}
