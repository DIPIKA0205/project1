package com.eshoppingcart.profileservice.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eshoppingcart.profileservice.jwt.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}
