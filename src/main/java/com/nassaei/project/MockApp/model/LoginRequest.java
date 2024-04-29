package com.nassaei.project.MockApp.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String customerId;
    private String password;
}
