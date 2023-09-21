package com.coffeeshop.domain.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.Product;
import com.coffeeshop.domain.model.promotion.OrderTotalCalculator;

public interface PrinterService {

    void printProduct(Product product);
    void printOrderReceipt(Order order, OrderTotalCalculator totalCalculator);
}
