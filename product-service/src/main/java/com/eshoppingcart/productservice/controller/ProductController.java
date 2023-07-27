package com.eshoppingcart.productservice.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eshoppingcart.productservice.entity.ImageModel;
import com.eshoppingcart.productservice.entity.Product;
import com.eshoppingcart.productservice.exception.ProductNotFoundException;
import com.eshoppingcart.productservice.service.ProductService;
import com.eshoppingcart.productservice.service.SequenceGeneratorService;

@RestController
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	Logger logger = LoggerFactory.getLogger(ProductController.class);

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@Autowired
	SequenceGeneratorService service;

	/**
	 * Add product.
	 *
	 * @RequestPart product containing all details of product
	 * @RequestPart file containing details of image addProduct method throw
	 *              exception if product not added successfully.
	 */
	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Product addProduct(@RequestPart("product") Product product, @RequestPart("imageFile") MultipartFile[] file)
			throws ProductNotFoundException {
		product.setProductId(service.generateSequence(Product.SEQUENCE_NAME));

		try {
			Set<ImageModel> images = uploadImage(file);
			product.setProductImages(images);
			return productService.addProduct(product);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
		Set<ImageModel> imageModels = new HashSet<>();

		for (MultipartFile file : multipartFiles) {
			ImageModel imageModel = new ImageModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
			imageModels.add(imageModel);
		}
		return imageModels;
	}

	/**
	 * get all products.
	 *
	 * @RequestParam pageNumber for implementing pagination getAllProducts method
	 *               throw exception if product not found.
	 */
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "") String searchKey) throws ProductNotFoundException {
		List<Product> products = productService.getAllProducts(pageNumber, searchKey);
		System.out.println("Result size is " + products.size());
		return new ResponseEntity<>(products, HttpStatus.OK);

	}

	/**
	 * get product by particular id
	 * 
	 * @param productId id of the product
	 * @return product
	 * @throws ProductNotFoundException if product not found for id
	 */
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable("id") int productId) throws ProductNotFoundException {
		Product product = productService.getProductById(productId);
		return productService.getProductById(productId);
	}

	// get product by type
	/**
	 * get product by product type
	 * 
	 * @param productType
	 * @return all product with respect to product type
	 * @throws ProductNotFoundException
	 */
	@GetMapping("/type/{productType}")
	public ResponseEntity<List<Product>> getProductByType(@PathVariable String productType)
			throws ProductNotFoundException {
		List<Product> products = productService.getProductsByType(productType);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	/**
	 * get product by product name
	 * 
	 * @param productName
	 * @return all product with respect to product name
	 * @throws ProductNotFoundException
	 */
	@GetMapping("/name/{productName}")
	public Optional<Product> getProductByName(@PathVariable String productName) throws ProductNotFoundException {
		Optional<Product> product = Optional
				.ofNullable(((Optional<Product>) productService.getProductByName(productName)).orElseThrow(
						() -> new ProductNotFoundException("Product not found with name: " + productName)));
		return product;
	}

	/**
	 * get product by category
	 * 
	 * @param category
	 * @return all product with respect to product category
	 * @throws ProductNotFoundException
	 */
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category)
			throws ProductNotFoundException {
		List<Product> products = productService.getProductsByCategory(category);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	/**
	 * update product based on product id
	 * 
	 * @param productId
	 * @param product
	 * @return updated product
	 * @throws ProductNotFoundException
	 */
	@PutMapping("/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable int productId, @RequestBody Product product)
			throws ProductNotFoundException {
		if (product.getProductId() != productId) {
			throw new ProductNotFoundException("Product ID mismatch");
		}
		Product updatedProduct = productService.updateProduct(product);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	// delete product
	/**
	 * delete particular product
	 * 
	 * @param productId
	 * @return
	 * @throws ProductNotFoundException
	 */
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable int productId) throws ProductNotFoundException {
		productService.deleteProductById(productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * get product details depend on product id
	 * 
	 * @param isSingleProductCheckout
	 * @param productId
	 * @return
	 */

	@GetMapping({ "/getProductDetails/{isSingleProductCheckout}/{productId}" })
	public List<Product> getProductDetails(
			@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
			@PathVariable(name = "productId") Integer productId) {
		return productService.getProductDetails(isSingleProductCheckout, productId);
	}

}
