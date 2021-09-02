package com.tech.service;

import org.springframework.http.ResponseEntity;

public interface UserService {

	public ResponseEntity<Object> checkBalance(Long accountNumber, Integer pin) throws Exception;
	
	public ResponseEntity<Object> withdraw(Long accountNumber, Long amount, Integer pin) throws Exception;

}
