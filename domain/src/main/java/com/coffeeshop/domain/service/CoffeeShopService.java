package com.coffeeshop.domain.service;

import com.coffeeshop.domain.model.Order;

public interface CoffeeShopService {

    void printMenu();

    void printOrderReceipt(Order order);
}
