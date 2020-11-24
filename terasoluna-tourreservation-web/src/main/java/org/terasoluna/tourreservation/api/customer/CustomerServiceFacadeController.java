package org.terasoluna.tourreservation.api.customer;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.service.customer.CustomerService;
import org.terasoluna.tourreservation.domain.service.customer.facade.RegisterInputResource;

@RestController
@RequestMapping("/api/facade/customerservice")
public class CustomerServiceFacadeController {
	@Inject
	@Qualifier("customerServiceImpl")
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
}
