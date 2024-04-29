package com.nassaei.project.MockApp.service.impl;

import com.nassaei.project.MockApp.controller.CustomerRegistrationController;
import com.nassaei.project.MockApp.entity.Account;
import com.nassaei.project.MockApp.model.AccountRequest;
import com.nassaei.project.MockApp.repository.AccountRepository;
import com.nassaei.project.MockApp.service.AccountService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);
    private static final int ACCOUNT_NUMBER_LENGTH = 12;
    private static final String ACCOUNT_STATUS_ACTIVE = "ACTIVE";
    private static final String ACCOUNT_STATUS_CLOSED = "CLOSED";
    private static final String EMPTY_STRING = "";
    private static final Random random = new Random();

    @Autowired
    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account saveAccount(AccountRequest accountRequest) throws Exception {
        Account account = null;
        try {
            LOGGER.debug("[AccountServiceImpl - saveAccount] === customerId: " + accountRequest.getCustomerId());
            if (accountRequest.getCustomerId() != null && !EMPTY_STRING.equals(accountRequest.getCustomerId())) {
                //Generate and validate accountNo
                String accountNo = validateGeneratedAccountNumber();

                //Set value in entity
                account = new Account();
                account.setAccountNo(accountNo);
                account.setAccountType(accountRequest.getAccountType());
                account.setStatus(ACCOUNT_STATUS_ACTIVE);
                account.setAccountBalance(new BigDecimal(0));
                account.setCustomerId(accountRequest.getCustomerId());
                account.setCreatedDate(new Date());

                this.accountRepository.save(account);
            } else {
                throw new Exception("Customer Id could not be null");
            }
        } catch (Exception e){
            LOGGER.error("AccountServiceImpl - saveAccount] === Exception: " + e.getMessage());
            throw new Exception(e.getMessage());
        }

        return account;
    }

    public Account getAccountByNo(String accountNo) throws Exception {
        Account account = null;
        try {
            LOGGER.debug("[AccountServiceImpl - getAccountByNo] === accountNo: " + accountNo);
            account = (Account) this.accountRepository.getAccount(accountNo);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        LOGGER.error("[AccountServiceImpl - getAccountByNo] === return customerId: " + account.getCustomerId());
        return account;
    }

    public Account getAccountByCustomerId(String customerId) throws Exception {
        Account account = null;
        try {
            LOGGER.debug("[AccountServiceImpl - getAccountByCustomerId] === customerId: " + customerId);
            account = (Account) this.accountRepository.getAccountByCustomerId(customerId);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        LOGGER.debug("[AccountServiceImpl - getAccountByCustomerId] === return customerId: " + account.getCustomerId());
        return account;
    }

    @Transactional
    public void updateAccountBalance(Account account) throws Exception {
        try {
            LOGGER.debug("[AccountServiceImpl - updateAccountBalance] === customerId: " + account.getCustomerId());
            LOGGER.debug("[AccountServiceImpl - updateAccountBalance] === account.getAccountBalance() before: " + account.getAccountBalance());

            if (ACCOUNT_STATUS_ACTIVE.equals(account.getStatus())) {
                accountRepository.updateAccountBalance(account.getAccountBalance(), account.getAccountNo());
            } else {
                throw new Exception("Account is not active!");
            }
        } catch (Exception e){
            LOGGER.error("AccountServiceImpl - updateAccountBalance] === Exception: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void updateAccountStatus(Account account) throws Exception {
        try {
            LOGGER.debug("[AccountServiceImpl - updateAccountStatus] === customerId: " + account.getCustomerId());
            if (ACCOUNT_STATUS_ACTIVE.equals(account.getStatus())) {
                account.setStatus(ACCOUNT_STATUS_CLOSED);
                accountRepository.updateAccountStatus(account.getStatus(), account.getAccountNo());
            } else {
                throw new Exception("Account already CLOSED!");
            }
        } catch (Exception e){
            LOGGER.error("AccountServiceImpl - updateAccountStatus] === Exception: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    // Validate the generated 12-digit account number
    public static String validateGeneratedAccountNumber() {
        Set<String> existingAccountNumbers = new HashSet<>();
        while (true) {
            String accountNumber = generateRandomAccountNumber();
            if (existingAccountNumbers.contains(accountNumber)) {
                continue;
            }
            existingAccountNumbers.add(accountNumber);
            return accountNumber;
        }
    }

    // Generate a random 12-digit account number
    private static String generateRandomAccountNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            sb.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        return sb.toString();
    }
}
