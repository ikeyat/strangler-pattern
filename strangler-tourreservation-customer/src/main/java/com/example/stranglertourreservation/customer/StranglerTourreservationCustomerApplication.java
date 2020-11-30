package com.example.stranglertourreservation.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class StranglerTourreservationCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StranglerTourreservationCustomerApplication.class, args);
	}

}
