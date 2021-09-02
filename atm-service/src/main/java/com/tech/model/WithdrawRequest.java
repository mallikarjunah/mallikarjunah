package com.tech.model;

public class WithdrawRequest {

	private Long accountNumber;

	private Integer pin;

	private Long amount;

	public WithdrawRequest() {

	}

	public WithdrawRequest(Long accountNumber, Integer pin, Long amount) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.amount = amount;
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

}
