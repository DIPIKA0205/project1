package com.eshoppingcart.orderservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class Cart {

	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private int cartId;

	private Product product;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Cart(int cartId, Product product) {
		super();
		this.cartId = cartId;
		this.product = product;
		// this.user = user;
	}

	public Cart(Product product) {
		super();
		this.product = product;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", product=" + product + "]";
	}

}
