package com.secured_webshop.controller;


import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.secured_webshop.entity.Product;
import com.secured_webshop.entity.User;
import com.secured_webshop.service.ProductService;
import com.secured_webshop.service.ShoppingCartService;
import com.secured_webshop.service.UserService;
import com.secured_webshop.util.Pager;

import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {
	
	private static final int INITIAL_PAGE = 0;
	 
	private final Logger log =  LoggerFactory.getLogger(this.getClass());
	

	private UserService userService;


	@Autowired
	public void setUserService(UserService userService) {
		this.userService= userService;
	}
	
	@Autowired
	private ProductService productService;
	

	

	@RequestMapping("/")
	public String homepage() {
		return "index";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/about")
	public String about() {
		return "about";
	}
	
	@RequestMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	@GetMapping("/products")
	public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Product> products = productService.findAllProductsPageable(new PageRequest(evalPage, 5));
        Pager pager = new Pager(products);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/products");
        return modelAndView;
    }
	
	@RequestMapping("/registration")
	public String registration(Model model){
		model.addAttribute("user", new User());
		return "registration";
	}
	

	@PostMapping("/reg")
    public String reg(@ModelAttribute User userToRegister) {
		System.out.println(userToRegister.getActivation());
		System.out.println();
		log.info(userToRegister.getActivation());
		userService.registerUser(userToRegister);
//		emailService.sendActivationMessage(userToRegister);	
//		log.debug(userToRegister.getActivation());
//		log.debug(user.getPassword());
		
        return "auth/login";
	}
    
    @GetMapping("/activation/{code}")
    public String activation(@PathVariable("code") String code, HttpServletResponse response) {
    	String result = userService.userActivation(code);
    	return "auth/login";
 	}
    
    @PostMapping("/order")
    public String order(@ModelAttribute User user , ShoppingCartService shoppingCartService) {
    	
    	userService.userOrder(user, shoppingCartService);
    	
    	return "index";
    }
}

