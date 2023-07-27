package com.eshoppingcart.cartservice.cartservicenew.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eshoppingcart.cartservice.cartservicenew.exception.CartNotFoundException;
import com.eshoppingcart.cartservice.cartservicenew.model.Cart;

@Service
public interface CartService {

	public Cart	addToCart(Cart cart,int productId);
	public List<Cart>getCartDetails();
	public void deleteCartById(int cartId)throws CartNotFoundException;

}
