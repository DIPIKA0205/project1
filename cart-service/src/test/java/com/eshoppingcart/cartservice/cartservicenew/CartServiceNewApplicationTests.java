package com.eshoppingcart.cartservice.cartservicenew;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.client.RestTemplate;

import com.eshoppingcart.cartservice.cartservicenew.exception.CartNotFoundException;
import com.eshoppingcart.cartservice.cartservicenew.model.Cart;
import com.eshoppingcart.cartservice.cartservicenew.model.Product;
import com.eshoppingcart.cartservice.cartservicenew.repository.CartRepository;
import com.eshoppingcart.cartservice.cartservicenew.service.CartServiceImpl;

@SpringBootTest
class CartServiceNewApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Mock
	private CartRepository cartRepository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private CartServiceImpl cartService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void testGetCartDetails() {
		List<Cart> cartList = new ArrayList<>();
		cartList.add(new Cart());
		cartList.add(new Cart());

		when(cartRepository.findAll()).thenReturn(cartList);

		List<Cart> result = cartService.getCartDetails();

		assertNotNull(result);
		assertEquals(cartList.size(), result.size());
	}

	@Test
	public void testDeleteCartById() throws CartNotFoundException {
		int cartId = 1;

		cartService.deleteCartById(cartId);

		// Verify that deleteById was called once with the correct cartId
		verify(cartRepository, times(1)).deleteById(cartId);
	}

	@Test
	public void testDeleteCartById_CartNotFound() throws CartNotFoundException {
		int cartId = 1;

		// Throw an exception when deleteById is called with the given cartId
		doThrow(new EmptyResultDataAccessException(1)).when(cartRepository).deleteById(cartId);

		cartService.deleteCartById(cartId);
	}


    

}
