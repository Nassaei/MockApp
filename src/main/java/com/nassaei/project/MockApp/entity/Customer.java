package com.nassaei.project.MockApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "CUSTOMER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "CUSTOMER_NAME")
    private String name;

    @Column(name = "IC_NO")
    private String icNo;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CUSTOMER_PASSWORD")
    private String password;
}
