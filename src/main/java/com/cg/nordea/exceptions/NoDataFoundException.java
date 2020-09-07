package com.cg.nordea.exceptions;

/**
 * Exception class to handle custom exceptions related to fields of Portfolio policies.
 * 
 * @author Capgemini
 * @since 09/03/2020
 */
public class NoDataFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1816017730802916913L;

	public NoDataFoundException() {
		super();
	}

	public NoDataFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoDataFoundException(String message) {
		super(message);
	}
}
