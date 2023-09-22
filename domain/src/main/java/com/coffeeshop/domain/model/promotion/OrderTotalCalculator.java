package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.model.Order;

import javax.money.MonetaryAmount;

public interface OrderTotalCalculator {

    MonetaryAmount calculateTotal(Order order);
}
