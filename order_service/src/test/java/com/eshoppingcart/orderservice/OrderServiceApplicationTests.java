package com.eshoppingcart.orderservice;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.eshoppingcart.orderservice.controller.OrderController;
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
import com.eshoppingcart.orderservice.service.OrderService;
import com.eshoppingcart.orderservice.service.OrderServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderServiceApplicationTests {

	 @Mock
	    private OrderService orderService;

	    @InjectMocks
	    private OrderController orderController;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void placeOrder_ValidOrderInput_PlacesOrder() throws PlaceOrderException {
	        // Arrange
	        boolean isSingleProductCheckout = true;
	        OrderInput orderInput = new OrderInput();

	        // Act and Assert
	        assertDoesNotThrow(() -> orderController.placeOrder(isSingleProductCheckout, orderInput));

	        verify(orderService, times(1)).placeOrder(orderInput, isSingleProductCheckout);
	    }

	    @Test
	    void placeOrder_ExceptionInService_ThrowsPlaceOrderException() throws PlaceOrderException {
	        // Arrange
	        boolean isSingleProductCheckout = true;
	        OrderInput orderInput = new OrderInput();

	        doThrow(new RuntimeException("Failed to place order")).when(orderService).placeOrder(orderInput, isSingleProductCheckout);

	        // Act and Assert
	        assertThrows(PlaceOrderException.class, () -> orderController.placeOrder(isSingleProductCheckout, orderInput));

	        verify(orderService, times(1)).placeOrder(orderInput, isSingleProductCheckout);
	    }

	    @Test
	    void getOrderDetails_ReturnsOrderDetails() throws GetOrderDetailsException {
	        // Arrange
	        List<OrderDetail> expectedOrderDetails = new ArrayList<>();
	        expectedOrderDetails.add(new OrderDetail());
	        expectedOrderDetails.add(new OrderDetail());

	        when(orderService.getOrderDetails()).thenReturn(expectedOrderDetails);

	        // Act
	        List<OrderDetail> result = orderController.getOrderDetails();

	        // Assert
	        assertEquals(expectedOrderDetails, result);
	        verify(orderService, times(1)).getOrderDetails();
	    }

	    @Test
	    void getOrderDetails_ExceptionInService_ThrowsGetOrderDetailsException() throws GetOrderDetailsException {
	        // Arrange
	        when(orderService.getOrderDetails()).thenThrow(new RuntimeException("Failed to get order details"));

	        // Act and Assert
	        assertThrows(GetOrderDetailsException.class, orderController::getOrderDetails);

	        verify(orderService, times(1)).getOrderDetails();
	    }

	    @Test
	    void createTransaction_ValidAmount_CreatesTransaction() throws CreateTransactionException {
	        // Arrange
	        Double amount = 100.0;
	        TransactionDetails expectedTransaction = new TransactionDetails("1234", "INR", 100,"EXCRTYUHFL");

	        when(orderService.createTransaction(amount)).thenReturn(expectedTransaction);

	        // Act
	        TransactionDetails result = orderController.createTransaction(amount);

	        // Assert
	        assertEquals(expectedTransaction, result);
	        verify(orderService, times(1)).createTransaction(amount);
	    }

	    @Test
	    void createTransaction_ExceptionInService_ThrowsCreateTransactionException() throws CreateTransactionException {
	        // Arrange
	        Double amount = 100.0;

	        when(orderService.createTransaction(amount)).thenThrow(new RuntimeException("Failed to create transaction"));

	        // Act and Assert
	        assertThrows(CreateTransactionException.class, () -> orderController.createTransaction(amount));

	        verify(orderService, times(1)).createTransaction(amount);
	    }
	


}
