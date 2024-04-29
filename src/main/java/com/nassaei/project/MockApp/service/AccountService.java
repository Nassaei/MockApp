package com.nassaei.project.MockApp.service;

import com.nassaei.project.MockApp.entity.Account;
import com.nassaei.project.MockApp.model.AccountRequest;
import jakarta.transaction.Transactional;

public interface AccountService {
    public Account saveAccount(AccountRequest accountRequest) throws Exception;

    public Account getAccountByNo(String accountNo) throws Exception;

    public Account getAccountByCustomerId(String customerId) throws Exception;

    public void updateAccountBalance(Account account) throws Exception;

    public void updateAccountStatus(Account account) throws Exception;
}
