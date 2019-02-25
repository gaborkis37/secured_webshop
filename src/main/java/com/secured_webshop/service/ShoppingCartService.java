package com.secured_webshop.service;

import com.secured_webshop.exception.NotEnoughProductsInStockException;
import com.secured_webshop.entity.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface ShoppingCartService {

    void addProduct(Product product);

    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    void checkout() throws NotEnoughProductsInStockException;

    BigDecimal getTotal();
}