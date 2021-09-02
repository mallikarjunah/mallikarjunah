package com.tech.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

	@Id
	private Long accountNumber;

	private Integer pin;

	private Long openingBalance;

	private Long overdraft;

	public Account() {
		
	}

	public Account(Long accountNumber, Integer pin, Long openingBalance, Long overdraft) {
		super();
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.openingBalance = openingBalance;
		this.overdraft = overdraft;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getPin() {
		return pin;
	}

	public void setPin(Integer pin) {
		this.pin = pin;
	}

	public Long getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(Long openingBalance) {
		this.openingBalance = openingBalance;
	}

	public Long getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(Long overdraft) {
		this.overdraft = overdraft;
	}

}
