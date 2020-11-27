/*
 * Copyright(c) 2013 NTT DATA Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.terasoluna.tourreservation.domain.service.userdetails.facade;

import java.net.URI;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;

public class ApiInvokingReservationUserDetailsService implements UserDetailsService {
	@Inject
	RestTemplate restTemplate;

	@Value("${facade.baseurl}")
	String baseUrl;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		URI targetUri = UriComponentsBuilder.fromUriString(baseUrl).path("/customerservice").path("/findByUsername")
				.queryParam("userName", username).build().toUri();

		// TODO error handling
		Customer customer = restTemplate.getForObject(targetUri, Customer.class);
		if (customer == null) {
			throw new UsernameNotFoundException(username + " is not found.");
		}
		return new ReservationUserDetails(customer);
	}

}
