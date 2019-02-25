package com.secured_webshop.repository;
import org.springframework.data.repository.CrudRepository;

import com.secured_webshop.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String role);
	
}