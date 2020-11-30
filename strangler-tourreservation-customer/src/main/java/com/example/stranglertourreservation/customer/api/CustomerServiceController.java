package com.example.stranglertourreservation.customer.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.stranglertourreservation.customer.domain.model.Customer;
import com.example.stranglertourreservation.customer.domain.service.CustomerService;

@RestController
@RequestMapping("/api/modern/customerservice")
public class CustomerServiceController {
	@Autowired
	private CustomerService customerService;

	@GetMapping("/findone")
	public Customer findOne(@RequestParam("customerCode") String customerCode) {
		// TODO error handling
		return customerService.findOne(customerCode);
	}
	
	@PostMapping("/register")
	public Customer register(@RequestBody RegisterInputResource input) {
		// TODO error handling
		return customerService.register(input.getCustomer(), input.getRawPassword());
	}

	@GetMapping("/findByUsername")
	public Customer findByUserName(@RequestParam("userName") String userName) {
		// TODO error handling
		try {
			return customerService.findByUsername(userName);
		} catch (UsernameNotFoundException e) {
			return null;
		}
	}
}
