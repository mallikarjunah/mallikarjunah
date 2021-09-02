package com.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tech.exception.BadRequestException;
import com.tech.model.WithdrawRequest;
import com.tech.service.UserService;

@RestController
@RequestMapping("/atm-transaction")
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/check-balance")
	public ResponseEntity<Object> checkBalance(
			@RequestParam(name = "accountNumber", required = true) Long accountNumber,
			@RequestParam(name = "PIN", required = true) Integer pin) throws Exception {
		return userService.checkBalance(accountNumber, pin);
	}

	@PutMapping("/withdraw-cash")
	public ResponseEntity<Object> withdrawCash(@RequestBody WithdrawRequest request) throws Exception {

		if (request.getAmount() % 5 != 0)
			throw new BadRequestException("Please enter valid amount to withdraw.");

		return userService.withdraw(request.getAccountNumber(), request.getAmount(), request.getPin());
	}

}
