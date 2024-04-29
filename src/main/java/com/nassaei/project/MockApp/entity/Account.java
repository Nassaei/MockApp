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
@Table(name = "ACCT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ACCOUNT_BALANCE")
    private BigDecimal accountBalance;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "CREATED_DATE")
    private Date createdDate;
}
