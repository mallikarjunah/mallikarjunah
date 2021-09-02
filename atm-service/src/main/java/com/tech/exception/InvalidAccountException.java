package com.tech.exception;

public class InvalidAccountException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidAccountException() {
		super();
	}

	public InvalidAccountException(final String message) {
		super(message);
	}
}
