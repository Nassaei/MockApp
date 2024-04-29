package com.nassaei.project.MockApp;

import com.nassaei.project.MockApp.controller.CustomerRegistrationController;
import com.nassaei.project.MockApp.entity.Customer;
import com.nassaei.project.MockApp.model.CustomerRequest;
import com.nassaei.project.MockApp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MockAppApplicationTests {
	@Mock
	MockMvc mockMvc;

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerRegistrationController customerRegistrationController;

	// Define the test data as a stream of CustomerRequest objects
	static Stream<CustomerRequest> customerCreationData() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		return Stream.of(
				new CustomerRequest("John", "Cena", "990101010001", "address 1", "john.cena@example.com", "0123334455", encoder.encode("password"), ""),
				new CustomerRequest("Jane", "Smith", "990101010002", "address 2", "jane.smith@example.com", "0123334456", encoder.encode("password"), ""),
				new CustomerRequest("Michael", "Jackson", "990101010003", "address 3", "michael.jackson@example.com", "0123334457", encoder.encode("password"), ""),
				new CustomerRequest("Ali", "Abu", "990101010004", "address 1", "ali.abu@example.com", "0123334458", encoder.encode("password"), ""),
				new CustomerRequest("Abu", "Bakar", "990101010005", "address 2", "Abu.bakar@example.com", "0123334459", encoder.encode("password"), ""),
				new CustomerRequest("Cristiano", "Ronaldo", "990101010006", "address 3", "cristiano.ronaldo@example.com", "0123334450", encoder.encode("password"), ""),
				new CustomerRequest("Lionel", "Messi", "990101010007", "address 1", "lionel.messi@example.com", "0123334451", encoder.encode("password"), ""),
				new CustomerRequest("Muhammad", "Ali", "990101010008", "address 2", "muhammad.ali@example.com", "0123334452", encoder.encode("password"), ""),
				new CustomerRequest("Ahmad", "Albab", "990101010009", "address 1", "ahmad.albab@example.com", "0123334453", encoder.encode("password"), ""),
				new CustomerRequest("Chee Keong", "Lee", "990101010010", "address 2", "leeck@example.com", "0123334454", encoder.encode("password"), ""),
				new CustomerRequest("Ahmad", "Abu", "9901010100011", "address 3", "ahmad.abu@example.com", "0123334466", encoder.encode("password"), ""),
				new CustomerRequest("Nurul", "Atikah", "990101010012", "address 1", "nurul.atikah@example.com", "0123334467", encoder.encode("password"), ""),
				new CustomerRequest("Nur", "Ain", "990101010013", "address 2", "nur.ain@example.com", "0123334468", encoder.encode("password"), ""),
				new CustomerRequest("Kok Keong", "Lee", "990101010014", "address 3", "kklee@example.com", "0123334469", encoder.encode("password"), "")
		);
	}

	@Test
	public void testUserCreation() throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		CustomerRequest customerRequest = new CustomerRequest("John", "Cena", "990101010001", "address 1", "john.cena@example.com", "0123334455", encoder.encode("password"), "");
		Customer savedCustomer = new Customer();
		savedCustomer.setCustomerId("JOHNCENA");

		when(customerService.saveCustomer(customerRequest)).thenReturn(savedCustomer);

		mockMvc.perform(MockMvcRequestBuilders.post("/mockapp/cutomerRegistration")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(customerRequest)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value("JOHNCENA"));
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
