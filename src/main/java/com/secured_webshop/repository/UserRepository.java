package com.secured_webshop.repository;

import org.springframework.data.repository.CrudRepository;

import com.secured_webshop.entity.User;

public interface UserRepository extends CrudRepository<User,Long>{
	
	User findByEmail(String email);
	User findByActivation(String code);
	
}