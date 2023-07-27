package com.eshoppingcart.orderservice.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection = "orders")
public class OrderDetail {

	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	
    @Id
    private Integer orderId;
    
    @NotBlank(message = "order full name is required")
    private String orderFullName;
    private String orderFullOrder;
    
    @NotBlank(message = "contact number is required")
    private String orderContactNumber;
    private String orderAlternateContactNumber;
    private String orderStatus;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than zero")
    private int orderAmount;
    
    private Product product;
    private String transactionId;
    
    //default constructor
	public OrderDetail() {
		super();
		
	}

	public OrderDetail(Integer orderId, @NotBlank(message = "order full name is required") String orderFullName,
			String orderFullOrder, @NotBlank(message = "contact number is required") String orderContactNumber,
			String orderAlternateContactNumber, String orderStatus,
			@DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than zero") int orderAmount,
			Product product, String transactionId) {
		super();
		this.orderId = orderId;
		this.orderFullName = orderFullName;
		this.orderFullOrder = orderFullOrder;
		this.orderContactNumber = orderContactNumber;
		this.orderAlternateContactNumber = orderAlternateContactNumber;
		this.orderStatus = orderStatus;
		this.orderAmount = orderAmount;
		this.product = product;
		this.transactionId = transactionId;
	}
	
	

	public OrderDetail(@NotBlank(message = "order full name is required") String orderFullName, String orderFullOrder,
			@NotBlank(message = "contact number is required") String orderContactNumber,
			String orderAlternateContactNumber, String orderStatus,
			@DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than zero") int orderAmount,
			Product product, String transactionId) {
		super();
		this.orderFullName = orderFullName;
		this.orderFullOrder = orderFullOrder;
		this.orderContactNumber = orderContactNumber;
		this.orderAlternateContactNumber = orderAlternateContactNumber;
		this.orderStatus = orderStatus;
		this.orderAmount = orderAmount;
		this.product = product;
		this.transactionId = transactionId;
	}

	//getters and setters
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderFullName() {
		return orderFullName;
	}

	public void setOrderFullName(String orderFullName) {
		this.orderFullName = orderFullName;
	}

	public String getOrderFullOrder() {
		return orderFullOrder;
	}

	public void setOrderFullOrder(String orderFullOrder) {
		this.orderFullOrder = orderFullOrder;
	}

	public String getOrderContactNumber() {
		return orderContactNumber;
	}

	public void setOrderContactNumber(String orderContactNumber) {
		this.orderContactNumber = orderContactNumber;
	}

	public String getOrderAlternateContactNumber() {
		return orderAlternateContactNumber;
	}

	public void setOrderAlternateContactNumber(String orderAlternateContactNumber) {
		this.orderAlternateContactNumber = orderAlternateContactNumber;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderAlternateContactNumber, orderAmount, orderContactNumber, orderFullName, orderFullOrder,
				orderId, orderStatus, product, transactionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetail other = (OrderDetail) obj;
		return Objects.equals(orderAlternateContactNumber, other.orderAlternateContactNumber)
				&& orderAmount == other.orderAmount && Objects.equals(orderContactNumber, other.orderContactNumber)
				&& Objects.equals(orderFullName, other.orderFullName)
				&& Objects.equals(orderFullOrder, other.orderFullOrder) && Objects.equals(orderId, other.orderId)
				&& Objects.equals(orderStatus, other.orderStatus) && Objects.equals(product, other.product)
				&& Objects.equals(transactionId, other.transactionId);
	}
	
	
  
	
    
    
    
    
                                    
    
}
