package org.terasoluna.tourreservation.domain.service.customer.facade;

import org.terasoluna.tourreservation.domain.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterInputResource {
	private Customer customer;
	private String rawPassword;
}
