package com.nassaei.project.MockApp.controller;

import com.nassaei.project.MockApp.entity.Customer;
import com.nassaei.project.MockApp.model.CustomerRequest;
import com.nassaei.project.MockApp.model.CustomerResponse;
import com.nassaei.project.MockApp.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://mock.app.com:3000")
@RestController
@RequestMapping("/mockapp")
public class CustomerRegistrationController {
    private static final Logger LOGGER = LogManager.getLogger(CustomerRegistrationController.class);

    @Autowired
    CustomerService customerService;

    @PostMapping("/cutomerRegistration")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRequest customerRequest) throws Exception {
        CustomerResponse customerResponse = new CustomerResponse();
        try {
            LOGGER.info("[CustomerRegistrationController - registerCustomer]");
            //Call service to save customer info to DB
            Customer customer = (Customer) customerService.saveCustomer(customerRequest);

            if (customer != null) {
                customerResponse.setCustomerId(customer.getCustomerId());
                return ResponseEntity.ok(customerResponse);
            } else {
                throw new Exception("Unable to create customer!");
            }
        } catch (Exception e){
            LOGGER.error("CustomerRegistrationController - registerCustomer] === Exception:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/customerInquiry")
    public ResponseEntity<?> getCustomer(@RequestBody CustomerRequest customerRequest) throws Exception {
        Customer customer = null;
        try {
            LOGGER.debug("[CustomerRegistrationController - getCustomer] === CustomerRequest: " + customerRequest.getCustomerId());
            //Call service to save customer info to DB
            customer = (Customer) customerService.getCustomerById(customerRequest.getCustomerId());
            return ResponseEntity.ok(customer);
        } catch (Exception e){
            LOGGER.error("CustomerRegistrationController - getCustomer] === Exception:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
