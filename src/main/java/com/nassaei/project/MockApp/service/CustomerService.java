package com.nassaei.project.MockApp.service;

import com.nassaei.project.MockApp.entity.Customer;
import com.nassaei.project.MockApp.model.CustomerRequest;

public interface CustomerService {
    public Customer saveCustomer(CustomerRequest customer) throws Exception;

    public Customer getCustomerById(String customerId) throws Exception;
}
