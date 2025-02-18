package com.food.ordering.system.order.service.domain.exception;

import com.food.ordering.system.domain.exception.DomianException;

public class OrderDomainException extends DomianException {
	
	private static final long serialVersionUID = 1L;

	public OrderDomainException(String message) {
		super(message);
	}
	
	public OrderDomainException(String message, Throwable throwable) {
		super(message, throwable);
	}

	

}
