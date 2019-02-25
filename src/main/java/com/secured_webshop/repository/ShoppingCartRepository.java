package com.secured_webshop.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import com.secured_webshop.entity.Product;

public interface ShoppingCartRepository extends JpaRepository<Product, Long>{
	
	
	
}