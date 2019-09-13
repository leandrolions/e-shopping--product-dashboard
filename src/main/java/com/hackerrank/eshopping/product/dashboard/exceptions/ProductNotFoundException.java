package com.hackerrank.eshopping.product.dashboard.exceptions;

@SuppressWarnings("serial")
public class ProductNotFoundException extends Exception {

	
	public ProductNotFoundException() {
		super();
	}
	public ProductNotFoundException(String message) {
		super(message);
	}
	public ProductNotFoundException(String message,Throwable cause) {
		super(message,cause);
	}
}
