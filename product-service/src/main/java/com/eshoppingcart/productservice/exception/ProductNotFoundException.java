package com.eshoppingcart.productservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ProductNotFoundException extends Exception {

	public ProductNotFoundException(String s) {
		super(s);
	}

	public ProductNotFoundException() {
		super("Product not found !!");
	}
}
