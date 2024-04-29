package com.nassaei.project.MockApp.controller;

import com.nassaei.project.MockApp.entity.Account;
import com.nassaei.project.MockApp.model.AccountRequest;
import com.nassaei.project.MockApp.model.AccountResponse;
import com.nassaei.project.MockApp.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "http://mock.app.com:3000")
@RestController
@RequestMapping("/mockapp")
public class AccountController {
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @PostMapping("/accountCreation")
    public ResponseEntity<?> registerAccount(@RequestBody AccountRequest accountRequest) throws Exception {
        AccountResponse accountResponse = new AccountResponse();
        try {
            LOGGER.debug("[AccountController - registerAccount] === AccountRequest: " + accountRequest);
            //Call service to save customer info to DB
            Account account = (Account) this.accountService.saveAccount(accountRequest);

            if (account != null) {
                accountResponse.setAccountNo(account.getAccountNo());
                accountResponse.setCustomerId(account.getCustomerId());
                return ResponseEntity.ok(accountResponse);
            } else {
                throw new Exception("Account not saved");
            }
        } catch (Exception e){
            LOGGER.error("AccountController - registerAccount] === Exception:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/accountInquiry")
    public ResponseEntity<Account> getAccount(@RequestBody AccountRequest accountRequest) throws Exception {
        Account account = null;
        try {
            LOGGER.debug("[AccountController - getCustomer] === accountRequest: " + accountRequest.getCustomerId());

            //Inquiry from DB
            if(accountRequest.getCustomerId() != null)
                account = (Account) accountService.getAccountByCustomerId(accountRequest.getCustomerId());
            else if (accountRequest.getAccountNo() != null)
                account = (Account) accountService.getAccountByNo(accountRequest.getAccountNo());
            return ResponseEntity.ok(account);
        } catch (Exception e){
            LOGGER.error("AccountController - getCustomer] === Exception:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> depositCash(@RequestBody AccountRequest accountRequest) throws Exception {
        Account account = null;
        try {
            LOGGER.debug("[AccountController - depositCash] === CustomerRequest: " + accountRequest.getAccountNo());
            //Call service to save customer info to DB
            account = (Account) accountService.getAccountByNo(accountRequest.getAccountNo());

            if(account != null) {
                account.setAccountBalance(account.getAccountBalance().add(accountRequest.getAmount()));
                accountService.updateAccountBalance(account);
            }
            return ResponseEntity.ok(account);
        } catch (Exception e){
            LOGGER.error("AccountController - depositCash] === Exception:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawCash(@RequestBody AccountRequest accountRequest) throws Exception {
        Account account = null;
        try {
            LOGGER.debug("[AccountController - withdrawCash] === accountRequest: " + accountRequest.getAccountNo());
            //Call service to save customer info to DB
            account = (Account) accountService.getAccountByNo(accountRequest.getAccountNo());

            if(account != null) {
                BigDecimal balance = account.getAccountBalance();
                BigDecimal withdrawAmount = accountRequest.getAmount();
                int result = balance.compareTo(withdrawAmount);
                LOGGER.debug("[AccountController - withdrawCash] === result: " + result);
                if (result >= 0) {
                    balance = balance.subtract(withdrawAmount);
                    account.setAccountBalance(balance);
                    accountService.updateAccountBalance(account);
                } else {
                    throw new Exception("Insufficient Balance");
                }
            }
            return ResponseEntity.ok(account);
        } catch (Exception e){
            LOGGER.error("AccountController - withdrawCash] === Exception:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/closeAccount")
    public ResponseEntity<?> closeAccount(@RequestBody AccountRequest accountRequest) throws Exception {
        Account account = null;
        try {
            LOGGER.debug("[AccountController - closeAccount] === CustomerRequest: " + accountRequest.getAccountNo());
            //Call service to save customer info to DB
            account = (Account) accountService.getAccountByNo(accountRequest.getAccountNo());

            if(account != null) {
                accountService.updateAccountStatus(account);
            }
            return ResponseEntity.ok(account);
        } catch (Exception e){
            LOGGER.error("AccountController - closeAccount] === Exception:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
