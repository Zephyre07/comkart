package com.cg.nordea.exceptions;

/**
 * Exception class to handle custom exceptions.
 * 
 * @author Capgemini
 * @since 09/03/2020
 */
public class ComKartException extends Exception {

	private static final long serialVersionUID = -8453192529050470811L;

	public ComKartException() {
		super();
	}

	public ComKartException(String message) {
		super(message);
	}

	public ComKartException(String message, Throwable cause) {
		super(message, cause);
	}
}
