package com.eshoppingcart.productservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eshoppingcart.productservice.entity.Product;
import com.eshoppingcart.productservice.exception.ProductNotFoundException;
import com.eshoppingcart.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	RestTemplate restTemplate;

	private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public Product addProduct(Product product) throws ProductNotFoundException {
		try {
			return productRepository.save(product);
		} catch (Exception e) {
			logger.error("Failed to add product: {}", e.getMessage());
			throw new ProductNotFoundException("Failed to add product: " + e.getMessage());
		}
	}

	@Override
	public List<Product> getAllProducts(int pageNumber, String searchKey) throws ProductNotFoundException {
		Pageable pageable = PageRequest.of(pageNumber, 10); // page=0,size=10
		try {
			if (searchKey.equals("")) {
				Page<Product> pageProduct = productRepository.findAll(pageable);
				List<Product> allProducts = pageProduct.getContent();
				return allProducts;
			} else {
				return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
						searchKey, searchKey, pageable);
			}

		} catch (Exception e) {
			logger.error("Failed to retrieve products: {}", e.getMessage());
			throw new ProductNotFoundException("Failed to retrieve products: " + e.getMessage());
		}
	}

	@Override
	public Product getProductById(int productId) throws ProductNotFoundException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product with given Id not found"));
		return product;
	}

	@Override
	public Optional<Product> getProductByName(String productName) throws ProductNotFoundException {
		try {
			return productRepository.findByProductName(productName);
		} catch (Exception e) {
			logger.error("Failed to retrieve product by name: {}", e.getMessage());
			throw new ProductNotFoundException("Failed to retrieve product by name: " + e.getMessage());
		}
	}

	@Override
	public Product updateProduct(Product product) throws ProductNotFoundException {
		try {
			return productRepository.save(product);
		} catch (Exception e) {
			logger.error("Failed to update product: {}", e.getMessage());
			throw new ProductNotFoundException("Failed to update product: " + e.getMessage());
		}
	}

	@Override
	public void deleteProductById(int productId) throws ProductNotFoundException {
		try {
			productRepository.deleteById(productId);
		} catch (Exception e) {
			logger.error("Failed to delete product by ID: {}", e.getMessage());
			throw new ProductNotFoundException("Failed to delete product by ID: " + e.getMessage());
		}
	}

	@Override
	public List<Product> getProductsByCategory(String category) throws ProductNotFoundException {
		try {
			return productRepository.findByCategory(category);
		} catch (Exception e) {
			logger.error("Failed to retrieve products by category: {}", e.getMessage());
			throw new ProductNotFoundException("Failed to retrieve products by category: " + e.getMessage());
		}
	}

	@Override
	public List<Product> getProductsByType(String productType) throws ProductNotFoundException {
		try {
			return productRepository.findByProductType(productType);
		} catch (Exception e) {
			logger.error("Failed to retrieve products by type: {}", e.getMessage());
			throw new ProductNotFoundException("Failed to retrieve products by type: " + e.getMessage());
		}
	}

	public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
		if (isSingleProductCheckout && productId != 0) {
			// we are going to buy a single product
			List<Product> list = new ArrayList<>();
			Product product = productRepository.findById(productId).get();
			list.add(product);
			return list;
		}

		else {
			// we are going to checkout entire cart
			String cartServiceUrl = "http://localhost:8000/carts/getCartDetails";
			ResponseEntity<List<Product>> response = restTemplate.exchange(cartServiceUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Product>>() {
					});
			List<Product> productList = response.getBody();
			logger.debug("Received response from cart service: {}", productList);
			return productList;

		}

	}

}
