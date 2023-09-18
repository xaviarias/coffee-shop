package com.coffeeshop.domain.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.Product;

public interface Printer {

    void printProduct(Product product);

    void printOrderReceipt(Order order);

}
