package com.eshoppingcart.productservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eshoppingcart.productservice.entity.Product;
import com.eshoppingcart.productservice.exception.ProductNotFoundException;

@Service
public interface ProductService {

	// add product
	public Product addProduct(Product product) throws ProductNotFoundException;

	// get all product
	public List<Product> getAllProducts(int pageNumber, String searchKey) throws ProductNotFoundException;

	// get product by id
	public Product getProductById(int productId) throws ProductNotFoundException;

	// get product by name
	public Optional<Product> getProductByName(String productName) throws ProductNotFoundException;

	// update product
	public Product updateProduct(Product product) throws ProductNotFoundException;

	// delete product by id
	public void deleteProductById(int productId) throws ProductNotFoundException;

	// get product by category
	public List<Product> getProductsByCategory(String category) throws ProductNotFoundException;

	// get product by type
	public List<Product> getProductsByType(String productType) throws ProductNotFoundException;

	// get all product details
	public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId);

}
