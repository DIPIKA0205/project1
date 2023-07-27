package com.eshoppingcart.productservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eshoppingcart.productservice.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {
	
	Optional<Product> findByProductName(String productName);
    List<Product> findByCategory(String category);
    List<Product> findByProductType(String productType);
    public List<Product>findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
    		String key1,String key2,Pageable pageable);

}
