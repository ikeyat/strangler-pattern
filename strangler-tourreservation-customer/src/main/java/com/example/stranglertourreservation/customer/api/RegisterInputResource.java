package com.example.stranglertourreservation.customer.api;

import com.example.stranglertourreservation.customer.domain.model.Customer;

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
