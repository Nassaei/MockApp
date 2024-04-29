package com.nassaei.project.MockApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String icNo;
    private String address;
    private String email;
    private String phoneNo;
    private String password;
    private String customerId;
}
