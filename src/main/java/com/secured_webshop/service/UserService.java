package com.secured_webshop.service;

import com.secured_webshop.entity.User;

public interface UserService {
	
	public String registerUser(User user);

	public User findByEmail(String email);
	
	public String userActivation(String code);
	
	public String userOrder(User user, ShoppingCartService shoppingCartService);
	
}
	
