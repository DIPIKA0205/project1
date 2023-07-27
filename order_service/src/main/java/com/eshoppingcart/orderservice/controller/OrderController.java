package com.eshoppingcart.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshoppingcart.orderservice.exception.CreateTransactionException;
import com.eshoppingcart.orderservice.exception.GetOrderDetailsException;
import com.eshoppingcart.orderservice.exception.PlaceOrderException;
import com.eshoppingcart.orderservice.model.OrderDetail;
import com.eshoppingcart.orderservice.model.OrderInput;
import com.eshoppingcart.orderservice.model.TransactionDetails;
import com.eshoppingcart.orderservice.service.OrderService;

import com.eshoppingcart.orderservice.service.SequenceGeneratorService;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Autowired
	SequenceGeneratorService service;

	/**
	 * place order 
	 * @param isSingleProductCheckout check the product is single
	 * @param orderInput contain details of order
	 * @throws PlaceOrderException
	 */
	@PostMapping({ "/placeOrder/{isSingleProductCheckout}" })
	public void placeOrder(@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
			@RequestBody OrderInput orderInput) throws PlaceOrderException {
		OrderDetail orderDetail=new OrderDetail();
		orderDetail.setOrderId(service.generateSequence(OrderDetail.SEQUENCE_NAME));
		try {
			orderService.placeOrder(orderInput, isSingleProductCheckout);
		} catch (Exception e) {
			throw new PlaceOrderException("Failed to place the order: " + e.getMessage());
		}
	}

	/**
	 * get all the order details
	 * @return
	 * @throws GetOrderDetailsException
	 */
	@GetMapping({ "/getOrderDetails" })
	public List<OrderDetail> getOrderDetails() throws GetOrderDetailsException {
		try {
			return orderService.getOrderDetails();
		} catch (Exception e) {
			throw new GetOrderDetailsException("Failed to retrieve order details: " + e.getMessage());
		}
	}

	/**
	 * createTransaction for the order
	 * @param amount
	 * @return
	 * @throws CreateTransactionException
	 */
	@GetMapping({ "/createTransaction/{amount}" })
	public TransactionDetails createTransaction(@PathVariable(name = "") Double amount)
			throws CreateTransactionException {
		try {
			return orderService.createTransaction(amount);
		} catch (Exception e) {
			throw new CreateTransactionException("Failed to create transaction: " + e.getMessage());
		}

	}
}
