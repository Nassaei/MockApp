package com.nassaei.project.MockApp.controller;

import com.nassaei.project.MockApp.entity.Customer;
import com.nassaei.project.MockApp.model.LoginRequest;
import com.nassaei.project.MockApp.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    CustomerService customerService;

    @CrossOrigin(origins = "http://mock.app.com:3000")
    @PostMapping("/loginPage")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        Customer customer = null;
        try {
            LOGGER.debug("[LoginController - login] === CustomerRequest: " + loginRequest);
            //Retrieve customer from DB
            customer = (Customer) customerService.getCustomerById(loginRequest.getCustomerId());

            // Check if customer exists and return hash password
            if (customer != null) {
                loginRequest.setPassword(customer.getPassword());
                return ResponseEntity.ok(loginRequest);
            } else {
                throw new Exception("Unable to retrieve user!");
            }
        } catch (Exception e) {
            LOGGER.error("[LoginController - login] === Exception:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
