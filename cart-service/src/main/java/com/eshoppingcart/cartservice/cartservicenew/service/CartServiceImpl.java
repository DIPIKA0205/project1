package com.eshoppingcart.cartservice.cartservicenew.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eshoppingcart.cartservice.cartservicenew.exception.CartNotFoundException;
import com.eshoppingcart.cartservice.cartservicenew.model.Cart;
import com.eshoppingcart.cartservice.cartservicenew.model.Product;
import com.eshoppingcart.cartservice.cartservicenew.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {

	private CartRepository cartRepository;

	@Autowired
	private RestTemplate resTemplate;
	private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	public CartServiceImpl(CartRepository cartRepository) {
		this.cartRepository = cartRepository;

	}

	public Cart addToCart(Cart cart, int productId) {
		Product product = this.resTemplate.getForObject("http://localhost:9002/product/" + productId, Product.class);
		if (product != null) {
			cart.setProduct(product);
			return cartRepository.save(cart);
		}
		return null;
	}

	public List<Cart> getCartDetails() {
		return cartRepository.findAll();
	}

	@Override
	public void deleteCartById(int cartId)throws CartNotFoundException {
		try {
			cartRepository.deleteById(cartId);
		} catch (Exception e) {
			logger.error("Failed to delete product by ID: {}", e.getMessage());

		}
	}

}
