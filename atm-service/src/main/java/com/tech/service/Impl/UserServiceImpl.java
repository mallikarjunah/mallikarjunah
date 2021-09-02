package com.tech.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tech.dao.AccountRepository;
import com.tech.dao.AtmMachineRepository;
import com.tech.exception.CannotDispenseThanItHoldsException;
import com.tech.exception.InsufficientFunds;
import com.tech.exception.InvalidAccountException;
import com.tech.model.entities.Account;
import com.tech.model.entities.AtmMachine;
import com.tech.service.UserService;
import com.tech.utils.Messages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AtmMachineRepository atmMachineRepository;

	@Autowired
	private Messages msg;

	@Override
	public ResponseEntity<Object> checkBalance(Long accountNumber, Integer pin) throws Exception {
		Account _account = getAccount(accountNumber, pin);
		HashMap<String, Long> response = new HashMap<>();
		response.put("availableBalance", _account.getOpeningBalance());
		if (_account.getOpeningBalance() > Long.valueOf(msg.getWithdrawLimit())) {
			response.put("maximumWithdrawal", Long.valueOf(msg.getWithdrawLimit()));
		} else {
			response.put("maximumWithdrawal", _account.getOpeningBalance());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> withdraw(Long accountNumber, Long amount, Integer pin) throws Exception {
		Account _account = getAccount(accountNumber, pin);
		if (amount > _account.getOpeningBalance()) {
			throw new InsufficientFunds("Insufficient Funds in your account");
		}
		
		Long availableCash = atmMachineRepository.availableCash();
		if (amount > availableCash) {
			throw new CannotDispenseThanItHoldsException("cannot dispense more money than it holds");
		}
		
		HashMap<Integer, Long> map = withdraw(amount, getNotes().stream().mapToInt(i -> i).toArray());

		Long openingBalance = _account.getOpeningBalance();
		_account.setOpeningBalance(openingBalance - amount);
		accountRepository.save(_account);

		for (Entry<Integer, Long> entry : map.entrySet()) {
			switch (entry.getKey()) {
			case 50:
				updateAtmMachine("FIFTY", entry.getValue());
				break;
			case 20:
				updateAtmMachine("TWENTY", entry.getValue());
				break;
			case 10:
				updateAtmMachine("TEN", entry.getValue());
				break;
			case 5:
				updateAtmMachine("FIVE", entry.getValue());
				break;
			default:
				break;
			}
		}

		HashMap<String, String> response = new HashMap<>();
		response.put("status", "Successfully withdrawn cash");
		response.put("remainingBalance", _account.getOpeningBalance().toString());
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	public Account getAccount(Long accountNumber, Integer pin) throws Exception {
		Account _account = accountRepository.findByAccountNumberAndPin(accountNumber, pin);
		if (_account == null || !_account.getPin().equals(pin)) {
			throw new InvalidAccountException("PIN entered is incorrect. Please enter correct PIN");
		}
		return _account;
	}

	private void updateAtmMachine(String key, Long value) {
		AtmMachine _data = atmMachineRepository.findById(key).get();
		Long count = _data.getCount();
		_data.setCount(count - value);
		atmMachineRepository.save(_data);
	}

	public HashMap<Integer, Long> withdraw(Long amount, int[] noteValues) {
		HashMap<Integer, Long> notes = new HashMap<>();
		for (int i = 0; i < noteValues.length && amount != 0; i++) {
			if (amount >= noteValues[i])
				notes.put(noteValues[i], amount / noteValues[i]);
			amount = amount % noteValues[i];
		}
		return notes;
	}

	private List<Integer> getNotes() {
		List<Integer> note = new ArrayList<Integer>();
		List<AtmMachine> list = atmMachineRepository.findAll();
		for (AtmMachine item : list) {
			if (item.getCount() > 0) {
				switch (item.getType()) {
				case "FIFTY":
					note.add(50);
					break;
				case "TWENTY":
					note.add(20);
					break;
				case "TEN":
					note.add(10);
					break;
				case "FIVE":
					note.add(5);
				default:
					break;
				}
			}
		}
		return note;
	}
}
