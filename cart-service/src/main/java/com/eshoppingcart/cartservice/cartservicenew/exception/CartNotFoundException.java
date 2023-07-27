package com.eshoppingcart.cartservice.cartservicenew.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CartNotFoundException extends Exception {
	
	public CartNotFoundException(String msg) {
		super(msg);
	}

}
