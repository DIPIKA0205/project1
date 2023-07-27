package com.eshoppingcart.orderservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eshoppingcart.orderservice.model.OrderDetail;


@Repository
public interface OrderRepository extends MongoRepository<OrderDetail,Integer>{

    
    
}
