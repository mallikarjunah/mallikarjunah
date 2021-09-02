package com.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tech.dao.AtmMachineRepository;
import com.tech.exception.BadRequestException;
import com.tech.model.entities.AtmMachine;

@RestController
public class AdminController {

	@Autowired
	AtmMachineRepository machineRepository;

	@PutMapping("/update-cash-in-machine")
	public ResponseEntity<Object> updateCash(@RequestParam(name = "type", required = true) String type,
			@RequestBody AtmMachine atmMachine) throws Exception {
		if (!type.equalsIgnoreCase("fifty") && !type.equalsIgnoreCase("twenty") && !type.equalsIgnoreCase("ten")
				&& !type.equalsIgnoreCase("five"))
			throw new BadRequestException("Invalid type entered.");

		return new ResponseEntity<>(machineRepository.save(atmMachine), HttpStatus.OK);
	}

	@PutMapping("/get-available-cash")
	public ResponseEntity<Object> getAvailableCash() throws Exception {
		return new ResponseEntity<>(machineRepository.findAll(), HttpStatus.OK);
	}

}
