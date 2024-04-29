package com.nassaei.project.MockApp.service.impl;

import com.nassaei.project.MockApp.entity.Customer;
import com.nassaei.project.MockApp.model.CustomerRequest;
import com.nassaei.project.MockApp.repository.CustomerRepository;
import com.nassaei.project.MockApp.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository accountRepository){
        this.customerRepository = accountRepository;
    }

    public Customer saveCustomer(CustomerRequest customerRequest) throws Exception {
        Customer returnCustomer = null;
        try {
            //Generate user ID
            String customerId = generateCustomerId(customerRequest.getFirstName(), customerRequest.getLastName());
            //Get customer from DB if exist
            returnCustomer = (Customer) getCustomerById(customerId);

            LOGGER.debug("[CustomerServiceImpl - saveCustomer] === customerId: " + customerId);
            LOGGER.debug("[CustomerServiceImpl - saveCustomer] === returnCustomer: " + returnCustomer);

            if(returnCustomer == null) {
                //Set value in entity
                Customer customer = new Customer();
                customer.setCustomerId(customerId);
                customer.setName(customerRequest.getFirstName().concat(" ").concat(customerRequest.getLastName()));
                customer.setIcNo(customerRequest.getIcNo());
                customer.setAddress(customerRequest.getAddress());
                customer.setEmail(customerRequest.getEmail());
                customer.setPhoneNo(customerRequest.getPhoneNo());
                customer.setCreatedDate(new Date());
                customer.setPassword(customerRequest.getPassword());
                //Call repository to save to DB
                this.customerRepository.save(customer);
                //Return successful customer info
                return customer;
            } else {
                if(returnCustomer.getIcNo().equals(customerRequest.getIcNo())) {
                    throw new Exception("User already exist");
                } else {
                    //Re-generate customerId by appending 3 random no at the back and save to DB
                    LOGGER.info("[CustomerServiceImpl - saveCustomer] === Re-generate unique customerId");
                    Random random = new Random();
                    int randomNumber = random.nextInt(1000);
                    String threeDigitNumber = String.format("%03d", randomNumber);

                    customerId = customerId.length() >= 20 ? customerId.substring(0,17).concat(threeDigitNumber) : customerId.concat(threeDigitNumber);
                    LOGGER.debug("[CustomerServiceImpl - saveCustomer] === new customerId: " + customerId);

                    //Set value in entity
                    Customer customer = new Customer();
                    customer.setCustomerId(customerId);
                    customer.setName(customerRequest.getFirstName().concat(" ").concat(customerRequest.getLastName()));
                    customer.setIcNo(customerRequest.getIcNo());
                    customer.setAddress(customerRequest.getAddress());
                    customer.setEmail(customerRequest.getEmail());
                    customer.setPhoneNo(customerRequest.getPhoneNo());
                    customer.setCreatedDate(new Date());
                    //Call repository to save to DB
                    this.customerRepository.save(customer);
                    //Return successful customer info
                    return customer;
                }
            }
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public Customer getCustomerById(String customerId) throws Exception {
        Customer returnCustomer = null;
        try {
            LOGGER.debug("[CustomerServiceImpl - getCustomerById] === customerId: " + customerId);
            returnCustomer = (Customer) this.customerRepository.getCustomer(customerId);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        LOGGER.debug("[CustomerServiceImpl - getCustomerById] === returnCustomer: " + returnCustomer);
        return returnCustomer;
    }

    public static String generateRandomHexId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public static String generateCustomerId(String firstName, String lastName) {
        //To get the last value of firstName
        String[] firstNameParts = firstName.trim().split("\\s+");
        firstName = firstNameParts[firstNameParts.length - 1];
        firstName = firstName.length() > 10 ? firstName.substring(0,10) : firstName;

        //To get the last value of lastName
        String[] lastNameParts = lastName.trim().split("\\s+");
        lastName = lastNameParts[lastNameParts.length - 1];
        lastName = lastName.length() > 10 ? lastName.substring(0,10) : lastName;

        String customerId = firstName.toUpperCase().concat(lastName.toUpperCase());

        LOGGER.debug("CustomerServiceImpl - generateCustomerId] === First Name: " + firstName);
        LOGGER.debug("CustomerServiceImpl - generateCustomerId] === Last Name: " + lastName);
        LOGGER.debug("CustomerServiceImpl - generateCustomerId] === Customer Id: " + customerId);

        return customerId;
    }
}
