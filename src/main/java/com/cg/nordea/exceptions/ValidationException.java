package com.cg.nordea.exceptions;

/**
 * Exception class to handle custom validation related exceptions.
 * 
 * @author Capgemini
 * @since 09/03/2020
 */
public class ValidationException extends Exception {

	private static final long serialVersionUID = 7733209621979119558L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
