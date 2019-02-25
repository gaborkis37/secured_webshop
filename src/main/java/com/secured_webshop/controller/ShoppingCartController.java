package com.secured_webshop.controller;

import com.secured_webshop.entity.User;
import com.secured_webshop.exception.NotEnoughProductsInStockException;
import com.secured_webshop.service.ProductService;
import com.secured_webshop.service.ShoppingCartService;
import com.secured_webshop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final ProductService productService;
    
    private UserService userService;
    

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService ,UserService userService ) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.userService=userService;
    }
    
    

    
    
    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/shoppingCart");
        modelAndView.addObject("products", shoppingCartService.getProductsInCart());
        modelAndView.addObject("total", shoppingCartService.getTotal().toString());
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::addProduct);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout() {
        try {
            shoppingCartService.checkout();
        } catch (NotEnoughProductsInStockException e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
        return shoppingCart();
    }
    
    
}