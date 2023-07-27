package com.eshoppingcart.productservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.eshoppingcart.productservice.entity.Product;
import com.eshoppingcart.productservice.exception.ProductNotFoundException;
import com.eshoppingcart.productservice.repository.ProductRepository;
import com.eshoppingcart.productservice.service.ProductServiceImpl;

@SpringBootTest
class ProductServiceApplicationTests {

	@Autowired
	private ProductServiceImpl productService;

	@MockBean
	private ProductRepository productRepository;
	private List<Product> product;

	@BeforeEach
	public void setproductservice() {

		product = new ArrayList<>();
		product.add(new Product(1, "Notebook", "Laptop", "Electronics", 90000, "It is a electronic device"));
		product.add(new Product(2, "panda", "toy", "stufftoy", 8999, "This is toy product"));

	}

	@Test
	void contextLoads() {
	}

	@Test
	void addProduct_ShouldSaveProduct() throws ProductNotFoundException {
		// Arrange
		Product product = new Product(1, "Notebook", "Laptop", "Electronics", 90000, "It is a electronic device");
		when(productRepository.save(product)).thenReturn(product);

		// Act
		Product result = productService.addProduct(product);

		// Assert
		assertNotNull(result);
		assertEquals(product.getProductId(), result.getProductId());
		assertEquals(product.getProductName(), result.getProductName());
		assertEquals(product.getCategory(), result.getCategory());
		assertEquals(product.getPrice(), result.getPrice());

		verify(productRepository, times(1)).save(product);
	}

	@Test
	void getAllProducts_ShouldReturnAllProducts() throws ProductNotFoundException {
		// Arrange
		int pageNumber = 0;
		String searchKey = "";

		Pageable pageable = PageRequest.of(pageNumber, 10);
		List<Product> products = new ArrayList<>();
		products.add(new Product(1, "Notebook", "Laptop", "Electronics", 90000, "It is a electronic device"));
		products.add(new Product(2, "panda", "toy", "stufftoy", 8999, "This is toy product"));
		Page<Product> pageProduct = new PageImpl<>(products, pageable, products.size());

		when(productRepository.findAll(pageable)).thenReturn(pageProduct);

		// Act
		List<Product> result = productService.getAllProducts(pageNumber, searchKey);

		// Assert
		assertNotNull(result);
		assertEquals(products.size(), result.size());
		assertEquals(products.get(0), result.get(0));
		assertEquals(products.get(1), result.get(1));

		verify(productRepository, times(1)).findAll(pageable);
	}

	@Test
	void getProductById_ExistingProductId_ShouldReturnProduct() throws ProductNotFoundException {
		// Arrange
		int productId = 1;
		Product product = new Product(1, "Notebook", "Laptop", "Electronics", 90000, "It is a electronic device");

		when(productRepository.findById(productId)).thenReturn(Optional.of(product));

		// Act
		Product result = productService.getProductById(productId);

		// Assert
		assertNotNull(result);
		assertEquals(productId, result.getProductId());
		assertEquals(product.getProductName(), result.getProductName());
		assertEquals(product.getCategory(), result.getCategory());

		verify(productRepository, times(1)).findById(productId);
	}

	@Test
	void getProductById_NonExistingProductId_ShouldThrowProductNotFoundException() {
		// Arrange
		int nonExistingProductId = 999;

		when(productRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(ProductNotFoundException.class, () -> productService.getProductById(nonExistingProductId));

		verify(productRepository, times(1)).findById(nonExistingProductId);
	}

	@Test
	void getProductByName_ExistingProductName_ShouldReturnOptionalProduct() throws ProductNotFoundException {
		// Arrange
		String productName = "Laptop";
		Product product = new Product(1, "Notebook", "Laptop", "Electronics", 90000, "It is a electronic device");

		when(productRepository.findByProductName(productName)).thenReturn(Optional.of(product));

		// Act
		Optional<Product> result = productService.getProductByName(productName);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(productName, result.get().getProductName());
		assertEquals(product.getCategory(), result.get().getCategory());

		verify(productRepository, times(1)).findByProductName(productName);
	}

	@Test
	void updateProduct_ValidProduct_ShouldReturnUpdatedProduct() throws ProductNotFoundException {
		// Arrange
		Product product = new Product(1, "Notebook", "Laptop", "Electronics", 90000, "It is a electronic device");

		when(productRepository.save(product)).thenReturn(product);

		// Act
		Product result = productService.updateProduct(product);

		// Assert
		assertNotNull(result);
		assertEquals(product.getProductId(), result.getProductId());
		assertEquals(product.getProductName(), result.getProductName());
		assertEquals(product.getCategory(), result.getCategory());

		verify(productRepository, times(1)).save(product);
	}

	@Test
	void deleteProductById_ExistingProductId_ShouldDeleteProduct() throws ProductNotFoundException {
		// Arrange
		int productId = 1;

		// No need to mock the behavior of productRepository.deleteById(productId)

		// Act
		productService.deleteProductById(productId);

		// Assert
		verify(productRepository, times(1)).deleteById(productId);
	}

	@Test
	void deleteProductByIdNonExistingProductId() {
		// Arrange
		int nonExistingProductId = 999;

		doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingProductId);

		// Act and Assert
		assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(nonExistingProductId));

		verify(productRepository, times(1)).deleteById(nonExistingProductId);
	}

}