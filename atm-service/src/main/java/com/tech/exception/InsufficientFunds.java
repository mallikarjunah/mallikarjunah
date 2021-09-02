package com.tech.exception;

public class InsufficientFunds extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientFunds() {
		super();
	}

	public InsufficientFunds(final String message) {
		super(message);
	}
}
