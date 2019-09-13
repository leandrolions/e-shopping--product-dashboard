package com.hackerrank.eshopping.product.dashboard.exceptions;

@SuppressWarnings("serial")
public class ProductConstraintViolationException extends Exception {

	
	public ProductConstraintViolationException() {
		super();
	}
	public ProductConstraintViolationException(String message) {
		super(message);
	}
	public ProductConstraintViolationException(String message,Throwable cause) {
		super(message,cause);
	}
}
