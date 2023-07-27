package com.eshoppingcart.profileservice.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eshoppingcart.profileservice.jwt.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
