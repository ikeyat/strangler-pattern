package org.terasoluna.tourreservation.domain.service.customer.facade;

import java.net.URI;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.service.customer.CustomerService;

@Primary
@Service
public class ApiInvokingCustomerServiceImpl implements CustomerService {
	@Inject
	RestTemplate restTemplate;

	@Value("${facade.baseurl}")
	String baseUrl;

	@Override
	public Customer findOne(String customerCode) {
		URI targetUri = UriComponentsBuilder.fromUriString(baseUrl).path("/customerservice").path("/findone")
				.queryParam("customerCode", customerCode).build().toUri();

		// TODO error handling
		return restTemplate.getForObject(targetUri, Customer.class);
	}

	@Override
	public Customer register(Customer customer, String rawPassword) {
		RegisterInputResource input = new RegisterInputResource(customer, rawPassword);
		URI targetUri = UriComponentsBuilder.fromUriString(baseUrl).path("/customerservice").path("/register").build()
				.toUri();

		// TODO error handling
		return restTemplate.postForObject(targetUri, input, Customer.class);
	}

}
