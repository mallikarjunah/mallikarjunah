package com.tech.exception;

public class CannotDispenseThanItHoldsException extends Exception {

	private static final long serialVersionUID = 1L;

	public CannotDispenseThanItHoldsException() {
		super();
	}

	public CannotDispenseThanItHoldsException(final String message) {
		super(message);
	}
}
