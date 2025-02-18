package com.food.ordering.system.domain.exception;

public class DomianException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DomianException(String message, Throwable cause) {
		super(message, cause);
	}

	public DomianException(String message) {
		super(message);
	}

	
}
