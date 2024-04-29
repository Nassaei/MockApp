package com.nassaei.project.MockApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nassaei.project.MockApp.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer save(Customer customer);

    @Query(value = "SELECT cust FROM Customer cust WHERE cust.customerId =:customerId")
    Customer getCustomer(@Param("customerId") String customerId);
}
