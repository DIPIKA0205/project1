package com.eshoppingcart.cartservice.cartservicenew.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshoppingcart.cartservice.cartservicenew.exception.CartNotFoundException;
import com.eshoppingcart.cartservice.cartservicenew.model.Cart;
import com.eshoppingcart.cartservice.cartservicenew.repository.CartRepository;
import com.eshoppingcart.cartservice.cartservicenew.service.CartService;
import com.eshoppingcart.cartservice.cartservicenew.service.SequenceGeneratorService;

@RestController
@RequestMapping("/carts")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

	private CartService cartService;
	private final CartRepository cartRepository;

	@Autowired
	SequenceGeneratorService service;

	CartController(CartService cartService, CartRepository cartRepository) {
		this.cartService = cartService;
		this.cartRepository = cartRepository;
	}

	/**
	 * add product to cart
	 * @param productId
	 * @return
	 */
	@GetMapping("/addToCart/{productId}")
	public ResponseEntity<Cart> addToCart(@PathVariable int productId) {
		Cart cart = new Cart();
		cart.setCartId(service.generateSequence(Cart.SEQUENCE_NAME));
		Cart updatedCart = cartService.addToCart(cart, productId);
		if (updatedCart != null) {
			return ResponseEntity.ok(updatedCart);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * get all the cart details
	 * @return
	 */
	@GetMapping("/getCartDetails")
	public ResponseEntity<List<Cart>> getCartDetails() {
		List<Cart> cartDetails = cartService.getCartDetails();
		if (!cartDetails.isEmpty()) {
			return ResponseEntity.ok(cartDetails);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * delete the cart based on cartid
	 * @param cartId
	 * @return
	 */
	@DeleteMapping("/{cartId}")
	public ResponseEntity<Void> deleteCart(@PathVariable int cartId) throws CartNotFoundException {
		 cartService.deleteCartById(cartId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
