package com.eshoppingcart.orderservice.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "orders")
public class OrderInput {
	
	@NotBlank(message = "full name is required")
	private String fullName;
	
	@NotBlank(message = "address is required")
	private String fullAddress;
	
	@NotBlank(message = "Contact number is required")
	private String contactNumber;
	private String alternateContactNumber;
	private String transactionId;
	private List<OrderProductQuantity>orderProductQuantityList;
	private Cart cartId;
	
	//default constructor
	public OrderInput() {
		super();
		
	}

	//Parameterized constructor
	public OrderInput(@NotBlank(message = "full name is required") String fullName,
			@NotBlank(message = "address is required") String fullAddress,
			@NotBlank(message = "Contact number is required") String contactNumber, String alternateContactNumber,
			String transactionId, List<OrderProductQuantity> orderProductQuantityList, Cart cartId) {
		super();
		this.fullName = fullName;
		this.fullAddress = fullAddress;
		this.contactNumber = contactNumber;
		this.alternateContactNumber = alternateContactNumber;
		this.transactionId = transactionId;
		this.orderProductQuantityList = orderProductQuantityList;
		this.cartId = cartId;
	}

	//getters and setters
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAlternateContactNumber() {
		return alternateContactNumber;
	}

	public void setAlternateContactNumber(String alternateContactNumber) {
		this.alternateContactNumber = alternateContactNumber;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public List<OrderProductQuantity> getOrderProductQuantityList() {
		return orderProductQuantityList;
	}

	public void setOrderProductQuantityList(List<OrderProductQuantity> orderProductQuantityList) {
		this.orderProductQuantityList = orderProductQuantityList;
	}

	public Cart getCartId() {
		return cartId;
	}

	public void setCartId(Cart cartId) {
		this.cartId = cartId;
	}
	
	
	
	
	

}
