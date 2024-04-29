package com.nassaei.project.MockApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nassaei.project.MockApp.entity.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account save(Account account);

    @Query(value = "SELECT acct FROM Account acct WHERE acct.accountNo =:accountNo")
    Account getAccount(@Param("accountNo") String accountNo);

    @Query(value = "SELECT acct FROM Account acct WHERE acct.customerId =:customerId")
    Account getAccountByCustomerId(@Param("customerId") String customerId);

    @Modifying
    @Query("UPDATE Account acct SET acct.accountBalance = :accountBalance WHERE acct.accountNo = :accountNo")
    void updateAccountBalance(@Param("accountBalance") BigDecimal accountBalance, @Param("accountNo") String accountNo);

    @Modifying
    @Query("UPDATE Account acct SET acct.status = :status WHERE acct.accountNo = :accountNo")
    void updateAccountStatus(@Param("status") String status, @Param("accountNo") String accountNo);
}
