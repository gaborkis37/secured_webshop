package com.secured_webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class SecuredWebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuredWebshopApplication.class, args);
	}
}
