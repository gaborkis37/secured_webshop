package com.secured_webshop.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.secured_webshop.entity.Role;
import com.secured_webshop.entity.User;
import com.secured_webshop.repository.RoleRepository;
import com.secured_webshop.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;
	
	private EmailService emailService;
	private UserRepository userRepository;

	private RoleRepository roleRepository;
	private JavaMailSender javaMailSender;
	
	private ShoppingCartService shoppingCartService;

	

	private final String USER_ROLE = "USER";

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,ShoppingCartService shoppingCartService ) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.shoppingCartService = shoppingCartService;
	}
	
	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new UserDetailsImpl(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public String registerUser(User userToRegister) {
		User userCheck = userRepository.findByEmail(userToRegister.getEmail());

		if (userCheck != null)
			return "alreadyExists";

		Role userRole = roleRepository.findByRole(USER_ROLE);
		if (userRole != null) {
			userToRegister.getRoles().add(userRole);
		} else {
			userToRegister.addRoles(USER_ROLE);
		}
		
		userToRegister.setEnabled(false);
		String activationKey = generateKey();
		log.info(activationKey);
		userToRegister.setActivation(activationKey);
		
		
		emailService.sendActivationMessage(userToRegister);
		
		userRepository.save(userToRegister);
		
		return "ok";
	}

	public String generateKey()
    {
		String key = "";
		Random random = new Random();
		char[] word = new char[16]; 
		for (int j = 0; j < word.length; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}
		String toReturn = new String(word);
		log.debug("random code: " + toReturn);
		return new String(word);
    }

	@Override
	public String userActivation(String code) {
		User user = userRepository.findByActivation(code);
		if (user == null) {
		    return "noresult";
		}
	
		user.setEnabled(true);
		user.setActivation("");
		userRepository.save(user);
		return "ok";
	}

	@Override
	public String userOrder(User user, ShoppingCartService shoppingCartService) {
		user  = userRepository.findByEmail(user.getEmail());
		userRepository.save(user);
		
		shoppingCartService.getProductsInCart();
		
		emailService.sendOrderDetails(user , shoppingCartService);
		
		return "ok";
	}
	

}