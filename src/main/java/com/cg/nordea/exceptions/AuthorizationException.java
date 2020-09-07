package com.cg.nordea.exceptions;

/**
 * Exception class to handle custom authorization validation related exceptions.
 * 
 * @author Capgemini
 * @since 09/03/2020
 */
public class AuthorizationException extends Exception {

	private static final long serialVersionUID = -7982736708799843604L;

	public AuthorizationException() {
		super();
	}

	public AuthorizationException(String message) {
		super(message);
	}

	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

}
