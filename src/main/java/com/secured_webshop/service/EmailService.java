package com.secured_webshop.service;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.secured_webshop.entity.User;

@Service
public class EmailService  {
    private final Log log = LogFactory.getLog(this.getClass());
    
	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;
	
	private String MESSAGE_TO = "gaborkis37@gmail.com";
	
	private JavaMailSender javaMailSender;

	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}


	public void sendMessage(String activation,String email,String firstName,String lastName) {
		SimpleMailMessage message = null;
		
		try {
			message = new SimpleMailMessage();
			message.setFrom(MESSAGE_FROM);
			message.setTo(MESSAGE_TO);
			message.setSubject("Succesfull registration");
			message.setText("Dear " + firstName +" "+ lastName+ "! \n \n Thank you for the registration! \n\n Welcome to the webshop"
					+"\n \n Please activate your registration" +"  \n \n \n   http://localhost:8080" + "/activation"   + activation  );
			
			
		} catch (Exception e) {
			log.error("Hiba e-mail küldéskor az alábbi címre: " + email + "  " + e);
		}
		

	}
	
	public void sendActivationMessage(User userToRegister ) {
		SimpleMailMessage message = null;
		
		
		
		try {
			message = new SimpleMailMessage();
			message.setFrom(MESSAGE_FROM);
			message.setTo(userToRegister.getEmail());
			message.setSubject("Succesfull registration");
			message.setText("Dear " + userToRegister.getFirstName() +" "+ userToRegister.getLastName()+ "! \n \n Thank you for the registration! \n\n Welcome to the webshop"
					+"\n \n Please activate your registration" +"  \n \n \n   http://localhost:8080/activation/"  + userToRegister.getActivation()  );
			javaMailSender.send(message);
			
		} catch (Exception e) {
			log.error("Hiba e-mail küldéskor az alábbi címre: " + userToRegister.getEmail() + "  " + e);
		}
		

	}
	
	public void sendOrderDetails(User user , ShoppingCartService shoppingCartService) {
		SimpleMailMessage message = null;
		
		try {
			message = new SimpleMailMessage();
			message.setFrom(MESSAGE_FROM);
			message.setTo(MESSAGE_TO);
			message.setSubject("Succesfull registration");
			message.setText("Dear " + user.getFirstName() +" "+ user.getEmail()+ "! \n \n Thank you for the registration! \n\n Welcome to the webshop"
					+"\n \n Please activate your registration" +"  \n \n \n"+ shoppingCartService.getProductsInCart()   );
			
			
		} catch (Exception e) {
			log.error("Hiba e-mail küldéskor az alábbi címre: " + user.getEmail() + "  " + e);
		}
		

	}

}
	
	