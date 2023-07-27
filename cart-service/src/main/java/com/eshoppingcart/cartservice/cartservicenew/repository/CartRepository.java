package com.eshoppingcart.cartservice.cartservicenew.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eshoppingcart.cartservice.cartservicenew.model.Cart;


@Repository
public interface CartRepository extends MongoRepository<Cart,Integer> {

	

	
}
