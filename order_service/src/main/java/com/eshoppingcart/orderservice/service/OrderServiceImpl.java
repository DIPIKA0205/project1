package com.eshoppingcart.orderservice.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eshoppingcart.orderservice.exception.CreateTransactionException;
import com.eshoppingcart.orderservice.exception.GetOrderDetailsException;
import com.eshoppingcart.orderservice.exception.PlaceOrderException;
import com.eshoppingcart.orderservice.model.OrderDetail;
import com.eshoppingcart.orderservice.model.OrderInput;
import com.eshoppingcart.orderservice.model.OrderProductQuantity;
import com.eshoppingcart.orderservice.model.Product;
import com.eshoppingcart.orderservice.model.TransactionDetails;
import com.eshoppingcart.orderservice.repository.OrderRepository;
import com.eshoppingcart.orderservice.repository.ProductRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	private static final String KEY = "rzp_test_SPTtprIKvIF4Py";
	private static final String KEY_SECRET = "tVaEpJ7yUw5FEat1FFbDUXgd";
	private static final String CURRENCY = "INR";

	@Autowired
	RestTemplate restTemplate;

	
	private static final String ORDER_PLACED = "Placed";
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		
	}

	public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) throws PlaceOrderException {
		List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
		if (productQuantityList != null && !productQuantityList.isEmpty()) {
			for (OrderProductQuantity o : productQuantityList) {
				try {
					// Product product =
					// this.resTemplate.getForObject("http://localhost:9002/product/"+productId,Product.class);
					Product product = productRepository.findById(o.getProductId()).get();
					logger.info("Product Price: {}", product.getPrice());
					logger.info("Order Quantity: {}", o.getQuantity());

					// Perform the calculation
					int orderAmount = (int) (product.getPrice() * o.getQuantity());
					OrderDetail orderDetail = new OrderDetail(orderInput.getFullName(), orderInput.getFullAddress(),
							orderInput.getContactNumber(), orderInput.getAlternateContactNumber(), ORDER_PLACED,
							orderAmount,

							product, orderInput.getTransactionId());
					logger.info("Order Amount: {}", orderAmount);
					if (!isSingleProductCheckout) {
						String cartServiceUrl = "http://localhost:8000/carts/{cartId}";
						restTemplate.delete(cartServiceUrl, orderInput.getCartId());
					}
					orderRepository.save(orderDetail);
				} catch (NoSuchElementException e) {
					logger.error("Failed to find product with ID: {}", o.getProductId());
					
				} catch (Exception e) {
					logger.error("An error occurred while placing the order: {}", e.getMessage());
					
				}
			}
		} else {
			logger.warn("No product quantity list provided for placing the order");
			
		}
	}

	// razoprpay always consider the currency in small units therefore multiply by
	// 100
	public TransactionDetails createTransaction(Double amount) throws CreateTransactionException {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("amount", (amount * 100));
			jsonObject.put("currency", CURRENCY);
			RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
			Order order = razorpayClient.orders.create(jsonObject);
			TransactionDetails transactionDetails = prepareTransactionDetails(order);
			return transactionDetails;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	private TransactionDetails prepareTransactionDetails(Order order) {
		String orderId = order.get("id");
		String currency = order.get("currency");
		int amount = order.get("amount");

		TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
		return transactionDetails;
	}

	public List<OrderDetail> getOrderDetails() throws GetOrderDetailsException {
		return orderRepository.findAll();
	}

}
