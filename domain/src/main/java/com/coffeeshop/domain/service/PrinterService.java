package com.coffeeshop.domain.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.Product;

import javax.money.MonetaryAmount;

public interface PrinterService {

    void printProduct(Product product);
    void printOrderReceipt(Order order, MonetaryAmount total);
}
