package com.secured_webshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.secured_webshop.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	 Optional<Product> findById(Long id);
	
}