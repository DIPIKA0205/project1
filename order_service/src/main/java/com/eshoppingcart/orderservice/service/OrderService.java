package com.eshoppingcart.orderservice.service;

import java.util.List;

import com.eshoppingcart.orderservice.exception.CreateTransactionException;
import com.eshoppingcart.orderservice.exception.GetOrderDetailsException;
import com.eshoppingcart.orderservice.exception.PlaceOrderException;
import com.eshoppingcart.orderservice.model.OrderDetail;
import com.eshoppingcart.orderservice.model.OrderInput;
import com.eshoppingcart.orderservice.model.TransactionDetails;



public interface OrderService {

	public void placeOrder(OrderInput orderInput,boolean isSingleProductCheckout)throws PlaceOrderException;
	public TransactionDetails createTransaction(Double amount)throws CreateTransactionException;  
	public List<OrderDetail> getOrderDetails()throws GetOrderDetailsException;
    
}
