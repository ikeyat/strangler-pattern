package com.example.stranglertourreservation.customer.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class PasswordEncodingConfig {
	@Bean
	public Pbkdf2PasswordEncoder pbkdf2PasswordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DelegatingPasswordEncoder passwordEncoder(Pbkdf2PasswordEncoder pbkdf2PasswordEncoder,
			BCryptPasswordEncoder bcryptPasswordEncoder) {
		Map<String, PasswordEncoder> idToPasswordEncoder = new HashMap<>();
		idToPasswordEncoder.put("pbkdf2", pbkdf2PasswordEncoder);
		idToPasswordEncoder.put("bcrypt", bcryptPasswordEncoder);
		return new DelegatingPasswordEncoder("pbkdf2", idToPasswordEncoder);
	}
}
